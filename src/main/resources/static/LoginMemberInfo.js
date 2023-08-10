
// /Member 위치에 속한 html에서만 가능합니다.
// 쿠키가 해당 루트에 위치해있음.
var userId;


// 구동 테스트 필요
function getUserId() {
    return fetch('/Member/getUserId', {
        method: 'GET',
        credentials: 'include'
    }).then(response =>
        response.json()
    ).then(data => {
        if(data == null) {
            fetch('/Member/memberLogin',{
                method: 'GET'
            }); // 로그인 화면으로 리다이렉트
        }
    });
}