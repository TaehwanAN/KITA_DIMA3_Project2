
const memberId=$("#memberId").val();
const memberRole=$("#memberRole").val();

$(document).ready(function() {
    // 분석값 가져오기
    init();
    // 초기 설정: #page1을 제외한 모든 section 숨기기
    $("section:not(#page1)").hide();

    // 버튼 클릭 이벤트 정의
    defineButtonClickEvents();

    //제출하는 경우
    $("#doMatching").click(function(event) {
        event.preventDefault();  // 폼 제출을 막습니다.
    
        // 폼 데이터를 가져옵니다.
        let tradeType = $("input[name='tradeType']:checked").val();
        let tradeTypeKr = (tradeType == 'purchase') ? '구매' : '판매';
        let startDate = $("#startDate").val();
        let endDate = $("#endDate").val();
        let tradeAmount = $("#tradeAmount").val();
        let minPrice = $("#minPrice").val();
    
        let confirmation=confirm(`
        ${memberId}님 등록요청을 확인해주세요.
        총 거래전력량: ${tradeAmount}kWh
        거래기간: ${startDate} ~ ${endDate}
        최소매칭가격: ${minPrice}원
        이대로 ${tradeTypeKr}하시겠습니까?
        `)
        if (!confirmation){
            return;
        }
        alert("거래요청이 등록되었습니다. 매칭을 진행합니다.")

        $("#page3").hide();
        $("#matching").show();
        setTimeout(function() {
            $("#matching").hide();
            $("#page4").show();
        }, 3555);

        // AJAX 요청을 보냅니다.
        $.ajax({
            url: 'consult/register',
            type: 'GET',
            data: {
                "memberId":memberId,
                "tradeType": tradeType,
                "startDate": startDate,
                "endDate": endDate,
                "tradeAmount": tradeAmount,
                "minPrice": minPrice
            },
            success: function(response) {
                // 요청이 성공하면 실행할 코드를 여기에 작성합니다.
                
            },
            error: function(jqXHR, textStatus, errorThrown) {
                // 요청이 실패하면 실행할 코드를 여기에 작성합니다.
                console.error(textStatus, errorThrown);
            }
        });
    });

});
function init(){
    $.ajax({
        url: 'consult/prepare',
        type: 'GET',
        data:{
            "memberId": memberId
        },
        dataType: 'json',
        success: function(resp){
            getAnalysisPQ();
        }
    })
    
}
function defineButtonClickEvents() {
    $("#startConsult").click(function() {
        $("#page1").hide();
        $("#loading").show();
        setTimeout(function() {
            $("#loading").hide();
            $("#page2").show();
        }, 3555);
    });

    $("#startMatch").click(function() {
        $("#page2").hide();
        $("#page3").show();
    });

    // $("#doMatching").click(function() {
    //     $("#page3").hide();
    //     $("#matching").show();
    //     setTimeout(function() {
    //         $("#matching").hide();
    //         $("#page4").show();
    //     }, 3555);
    // }); ==> 매칭등록하는 쪽에서 다루는 걸로 돌림

    $("#doTrade").click(function() {

        alert("거래가 완료되었습니다. 한국전력공사로 거래 결과가 전송됩니다. 자세한 사항은 마이페이지를 확인해주세요.");
        $("#page4").hide();
        $("#page5").show();
    });
}

function getAnalysisPQ(){
    $.ajax({
        url:'consult/getAnalysisPQ',
        type: 'GET',
        data:{
            "memberId":memberId
        },
        dataType:'json',
        success: putAnalysisPQ
    })
}
//분석결과 가져오기
function putAnalysisPQ(resp){
    let analysisAmount=0;
    let analysisRevenue=0;
    let analysisType='';
    let progList=getProgList(resp['PredictConsumptionMonths'],resp['PredictConsumption']);
    // 소비만 하는 경우(일단 3구간에서의 차익만 계산)
    if(memberRole=='ROLE_CONSUMER'){
        for (let i=1; i<resp['PredictConsumption'].length; ++i){
            analysisAmount+=progList['prog3'][i]
            analysisRevenue+=progList['prog3'][i]*(resp['PredictPriceProg3'][i]-resp['PredictPriceProg1'][i])
        }
        analysisType='구매';

    } else{ //생산하는경우 -> 두가지 케이스: 1)생산>소비 , 2) 생산<소비
        for(let i=1;i<resp['PredictConsumptionMonths'].length; ++i){
            analysisAmount+=progList['prog3'][i]-resp['ActualProduction'][i];
            analysisRevenue+=(resp['PredictPriceProg3'][i]-resp['PredictPriceProg1'][i])*(progList['prog3'][i]-resp['ActualProduction'][i])
        }
        if(analysisAmount<0){
            analysisType='판매';
            analysisAmount=Math.abs(analysisAmount)
            analysisRevenue=Math.abs(analysisRevenue)
            
        } else{
            analysisType='구매';
        }

    }
    $('#analysisAmount').html(Math.round(analysisAmount));
    $('#analysisRevenue').html(formatCurrency(analysisRevenue));
    $('#analysisType').html(analysisType);

    // analysisType에 따라 색상 변경
    if (analysisType == '구매') {
        $('#analysisType').css('color', '#ff0066');
        $("input[name='tradeType'][value='purchase']").prop('checked', true);
    } else if (analysisType == '판매') {
        $('#analysisType').css('color', '#006600');
        $("input[name='tradeType'][value='sale']").prop('checked', true);
    }

    // tradeAmount에 analysisAmount를 디폴트로 설정
    $('#tradeAmount').val(Math.round(analysisAmount));

    // minPrice에 analysisRevenue의 70%를 디폴트로 설정
    $('#minPrice').val(Math.round(analysisRevenue * 0.7));
}
function getProgList(monthList,consList){
    let prog1List=[];
    let prog2List=[];
    let prog3List=[];
    for(let i = 0; i<monthList.length; ++i){
        if ((String(monthList[i]).endsWith('7월')) || (String(monthList[i]).endsWith('8월'))){
            if (consList[i]>450){
                prog1List.push(300);
                prog2List.push(150);
                prog3List.push(consList[i]-450);
            } else if(consList[i]>300){
                prog1List.push(300);
                prog2List.push(consList[i]-300);
                prog3List.push(0);
            } else{
                prog1List.push(consList[i]);
                prog2List.push(0);
                prog3List.push(0);
            }
        } else {
            if (consList[i]>400){
                prog1List.push(200);
                prog2List.push(200);
                prog3List.push(consList[i]-400);
            } else if(consList[i]>200){
                prog1List.push(200);
                prog2List.push(consList[i]-200);
                prog3List.push(0);
            } else{
                prog1List.push(consList[i]);
                prog2List.push(0);
                prog3List.push(0);
            }
        }
    }
    let prog={
        'prog1':prog1List, 'prog2':prog2List, 'prog3':prog3List
    }
    return prog;
}

function formatCurrency(num) {
    
    let price= Math.round(num);
    let unit1=Math.floor(price/10000);
    let unit2= price-(unit1*10000)

    return unit1+'만'+' '+ unit2 + '원';
}
