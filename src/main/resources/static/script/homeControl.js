$(document).ready(function() {
    const images = ['#mainimage1', '#mainimage2', '#mainimage3', '#mainimage4'];
    let index = 0;

    // 이미지 순환 함수
    function cycleImages() {
        const nextIndex = (index + 1) % images.length;

        $(images[index]).animate({left: '-100%'}, 700, function() {
            $(this).hide().css('left', '0');
        });

        $(images[nextIndex]).css('left', '100%').show().animate({left: '0'}, 700);

        index = nextIndex;
    }

    // 초기 설정
    $(images.join(',')).hide();
    $(images[index]).show();
    setInterval(cycleImages, 3500);

    // 이벤트 핸들러 설정
    $("#simulation").click(() => $("#simulModal").show());
    $(".close").click(() => $("#simulModal").hide());
    $('#simulBtn').on('click', insertSimul);
    $('#liveTrade').html(generateTrades(100));
});

// 랜덤 이름 생성 함수
function getRandomName() {
    const names = ['정다○','안태○','남지○','주진○','이길○','이태○','홍서○','김미○','오지○','이현○','심세○','전하○','강민○','김희○','김도○','김인○','윤주○','노재○','이상○','이길○','박수○'];
    return names[Math.floor(Math.random() * names.length)];
}

// 랜덤 기간 생성 함수
function getRandomPeriod() {
    const periods = [2, 3, 3, 3, 4, 5, 6, 7, 8, 9, 10];
    return periods[Math.floor(Math.random() * periods.length)];
}

// 랜덤 거래량 생성 함수
function getRandomVolume(period) {
    const min = 450;
    const max = 2000;
    const volumePerMonth = (max - min) / 9;
    return Math.round(min + volumePerMonth * (period - 2));
}

// 거래 내용 생성 함수
function generateTrade() {
    const name1 = getRandomName();
    const name2 = getRandomName();
    const period = getRandomPeriod();
    const volume = getRandomVolume(period);

    return `<ul><b>${name1}님과 ${name2}님의 ${period}개월 ${volume}kWh 거래가 체결되었습니다.</b></ul>`;
}

// 여러 거래 내용 생성 함수
function generateTrades(count) {
    let trades = '';
    for (let i = 0; i < count; i++) {
        trades += generateTrade();
    }
    return trades;
}

// 시뮬레이션 삽입 함수
function insertSimul(){
    const simulName = $('#simulName').val();
    const simulNationalIdFirst = $('#simulNationalIdFirst').val();
    const simulNationalIdSecond = $('#simulNationalIdSecond').val();
    
    // 이름이 입력되지 않은 경우
    if (!simulName) {
        alert('이름을 입력해주세요');
        return false;
    }

    // 주민등록번호 앞 6자리가 유효하지 않은 경우
    if (!/^\d{6}$/.test(simulNationalIdFirst)) {
        alert('유효한 주민등록번호 앞 6자리를 입력해주세요');
        return false;
    }

    // 주민등록번호 뒷 7자리가 유효하지 않은 경우
    if (!/^\d{7}$/.test(simulNationalIdSecond)) {
        alert('유효한 주민등록번호 뒷 7자리를 입력해주세요');
        return false;
    }

    if (!$('#simulAgree').is(':checked')) {
        alert('개인정보 이용동의를 체크하지 않으면, 결과확인이 불가합니다');
        return false;
    }
    
    $('#simulResult').html("분석 중...");
    setTimeout(() => {
        $.ajax({
            url: 'simulation',
            type: 'GET',
            data: {
                "simulName": simulName,
                "simulNationalIdFirst": simulNationalIdFirst,
                "simulNationalIdSecond": simulNationalIdSecond
            },
            dataType: '',
            success: function(resp){
                $('#simulResult').html(resp);
            }
        });
    }, 1000);
}