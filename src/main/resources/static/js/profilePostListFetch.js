
// 여러 개의 이미지를 가져와야 한다.
// 이미지 + 해당 게시글의 댓글 수까지 가져와서 마우스 오버 시, 댓글 개수가 보여야 함. => 시간이 가능할 경우 진행
// 결론적으로 프론트엔드로 각 게시글의 id, 이미지, 댓글 수를 끌어오도록 한다.
// 이후 해당 이미지가 클릭되는 경우 가져왔던 게시글id를 서버에 올려보내는 것으로 게시글의 나머지 정보를 추가적으로 가져와 보이도록 한다.


// 저장용도의 객체 형식




const userContentsMap = new Map;



// let iframe = document.querySelector("#userContents");

getUserContents();


function getUserContents() {
    const imgList= document.querySelector(".hardCoded_photo").querySelectorAll(".post_photo");
    for (let i = 0; i < imgList.length; i++) imgList[i].classList.add("hidden");

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
            // 게시글 관련 로드
            var i = 0;

            const profileContentsBox = document.getElementById("photo_box");

            profileContentsBox.innerHTML = ""; // 매 로드마다 초기화 진행

            for(i = 0; i < data.length; i++) {
                const userContents = {
                    postId : '',
                    thumbnailSrc : '',
                    commentsNumber : 0
                }

                var semi_post = data[i];
                userContents.thumbnailSrc = semi_post.imgSrc;
                userContents.commentsNumber = semi_post.commentsNum;

                userContentsMap.set(semi_post.postId, userContents);



                if (i != 0 && i % 3 == 0) {
                    profileContentsBox.appendChild(document.createElement('br'));
                }

                var postDiv = document.createElement('div');
                postDiv.classList.add('post_photo');
                postDiv.id = semi_post.postId;

                var preViewImg = document.createElement('img');
                preViewImg.src = "/postImages/" + semi_post.imgSrc;

                var button = document.createElement('button');
                button.className = 'post_button';
                button.id = semi_post.postId;

                var span = document.createElement('span');
                span.className = 'target';

                postDiv.appendChild(preViewImg);
                postDiv.appendChild(button);
                postDiv.appendChild(span);

                profileContentsBox.appendChild(postDiv);



            }
            console.log("불러온 게시글 내용은 다음과 같음." + userContentsMap);

            openEventMaking()
    });
}