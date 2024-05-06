$(document).ready(function() {
    // 초기 설정: #page1을 제외한 모든 section 숨기기
    $("section:not(#page1)").hide();

    // 버튼 클릭 이벤트 정의
    defineButtonClickEvents();

    
});

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

    $("#doMatching").click(function() {
        $("#page3").hide();
        $("#matching").show();
        setTimeout(function() {
            $("#matching").hide();
            $("#page4").show();
        }, 3555);
    });

    $("#doTrade").click(function() {

        alert("거래가 완료되었습니다. 한국전력공사로 거래 결과가 전송됩니다. 자세한 사항은 마이페이지를 확인해주세요.");
        $("#page4").hide();
        $("#page5").show();
    });
}