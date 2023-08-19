
// 여러 개의 이미지를 가져와야 한다.
// 이미지 + 해당 게시글의 댓글 수까지 가져와서 마우스 오버 시, 댓글 개수가 보여야 함. => 시간이 가능할 경우 진행
// 결론적으로 프론트엔드로 각 게시글의 id, 이미지, 댓글 수를 끌어오도록 한다.
// 이후 해당 이미지가 클릭되는 경우 가져왔던 게시글id를 서버에 올려보내는 것으로 게시글의 나머지 정보를 추가적으로 가져와 보이도록 한다.


// 저장용도의 객체 형식
const userContents = {
    postId : '',
    thumbnailSrc : '',
    commentsNumber : 0
}



const userContentsMap = new Map;

const profileContentsBox = document.getElementById("userContentBox");

getUserContents();


function getUserContents() {
    fetch("/Member/Profile/getUserContents",{
            method: 'GET',
            credentials: 'include'
        }).then(response => {
            if(response.ok) {
                return response.json();
            }
            else {
                throw new Error('유저 컨텐츠 가져오지 못함');
            }
        }).then(data => {
            console.log("실제의 경우: " + data);
            // 게시글 관련 로드
            for(var i = 0; i < data.length; i++) {
                var semi_post = data[i];
                userContents.postId = semi_post.postId;
                userContents.thumbnailSrc = semi_post.imgSrc;
                userContents.commentsNumber = semi_post.commentsNum;

                userContentsMap.set(i, userContents);


                // 각 게시글을 페이지에 배치
                var imgTag = "<img id = " + i + " src = \"/postImages/" + semi_post.imgSrc + "\">";
                profileContentsBox.innerHTML += imgTag;
            }
        });

}
