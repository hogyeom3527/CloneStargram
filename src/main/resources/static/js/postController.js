//게시물, 저장됨, 태그됨 버튼 css 변경 함수
const postt = document.getElementById("post");
const savee = document.getElementById("save");
const tagg = document.getElementById("tag");

function clickPost(){

    getUserContents();

    postt.style.borderTopColor="black";
    savee.style.borderTopColor="#d9d9d9";
    tagg.style.borderTopColor="#d9d9d9";

    document.getElementById("post_img").src="/images/post.png";
    document.getElementById("save_img").src="/images/saved.png";
    document.getElementById("tag_img").src="/images/tag.png";

    document.getElementById("post_span").style.color="black";
    document.getElementById("save_span").style.color="#737373";
    document.getElementById("tag_span").style.color="#737373";




}

function clickSave(){
    postt.style.borderTopColor="#d9d9d9";
    savee.style.borderTopColor="black";
    tagg.style.borderTopColor="#d9d9d9";

    document.getElementById("post_img").src="/images/post2.png";
    document.getElementById("save_img").src="/images/saved2.png";
    document.getElementById("tag_img").src="/images/tag.png";

    document.getElementById("post_span").style.color="#737373";
    document.getElementById("save_span").style.color="black";
    document.getElementById("tag_span").style.color="#737373";

    document.getElementById("photo1").src="/images/cat.jpg";
    document.getElementById("photo2").src="/images/cat2.jpg";
    document.getElementById("photo3").src="/images/cat3.jpg";

    const profileContentsBox = document.getElementById("photo_box");
    profileContentsBox.innerHTML = ""; // 매 로드마다 초기화 진행

    const imgList= document.querySelector(".hardCoded_photo").querySelectorAll(".post_photo");
    for (let i = 0; i < imgList.length; i++) imgList[i].classList.remove("hidden");

}

function clickTag(){

    postt.style.borderTopColor="#d9d9d9";
    savee.style.borderTopColor="#d9d9d9";
    tagg.style.borderTopColor="black";

    document.getElementById("post_img").src="/images/post2.png";
    document.getElementById("save_img").src="/images/saved.png";
    document.getElementById("tag_img").src="/images/tag2.png";

    document.getElementById("post_span").style.color="#737373";
    document.getElementById("save_span").style.color="#737373";
    document.getElementById("tag_span").style.color="black";

    document.getElementById("photo1").src="/images/cat4.jpg";
    document.getElementById("photo2").src="/images/cat5.jpg";
    document.getElementById("photo3").src="/images/cat6.jpg";

    const profileContentsBox = document.getElementById("photo_box");
    profileContentsBox.innerHTML = ""; // 매 로드마다 초기화 진행

    const imgList= document.querySelector(".hardCoded_photo").querySelectorAll(".post_photo");
    for (let i = 0; i < imgList.length; i++) imgList[i].classList.remove("hidden");
}

postt.addEventListener("click",clickPost);
savee.addEventListener("click",clickSave);
tagg.addEventListener("click",clickTag);


//게시물 내용창 여는 함수
const posting = document.querySelector(".check_post");

function openEventMaking() {
    const postBtnList = document.querySelectorAll(".post_button");
    const posting = document.querySelector(".check_post");

    function openPost(event){
        posting.classList.remove("hidden_post");

        const clickedPostId = event.target.id;

        fetch("/Member/getPostInfo/" + clickedPostId, {
            method: 'GET',
            credentials: 'include'
        }).then(
            (response) => {
                console.log(response);
                return response.json(); // 여러줄 사용 시 return으로 작성하기
            }

        ).then(
            (data) => {
                console.log(data);

                // 게시글 내 사용자 명에 삽입
                const userNameList = document.querySelectorAll(".userName");
                for(var i = 0; i < userNameList.length; i++)
                    userNameList[i].innerText = data['postUserId'];
                
                // 게시글 내용 삽입
                document.getElementById("postValue").innerText = data['postValue'];
                
                // 게시글 이미지 삽입
                document.getElementById("postImage").src = "/postImages/" + userContentsMap.get(clickedPostId).thumbnailSrc;



                // 시간 계산하기

            }
        );
    }

    for(var i = 0; i < postBtnList.length; i++)
        postBtnList[i].addEventListener("click", openPost);
}

//댓글 버튼 누르면 입력창에 커서가 포커스되는 함수

const writeComment = document.getElementById("comment");
function focus(){
    document.getElementById("write_comment").focus();
}
writeComment.addEventListener("click",focus);


//북마크, 북마크 취소 함수
let isBackgroundChanged = false; // 배경 이미지 변경 여부를 저장할 변수

function save() {
    const bookMark = document.getElementById("bookmark");

    if (isBackgroundChanged) {
        bookMark.style.backgroundImage = "url('../img/saved2.png')"; // 배경 이미지를 원래대로 변경
        isBackgroundChanged = false;
    } else {
        bookMark.style.backgroundImage = "url('../img/saved3.png')"; // 배경 이미지를 변경
        isBackgroundChanged = true;
    }
}

document.getElementById("bookmark").addEventListener("click", save);


//좋아요, 좋아요 취소 함수
let isBackgroundChangedAgain = false; // 배경 이미지 변경 여부를 저장할 변수

function likeYou() {
    const likeBtn = document.getElementById("like");

    if (isBackgroundChangedAgain) {
        likeBtn.style.backgroundImage = "url('../img/alert.png')"; // 배경 이미지를 원래대로 변경
        isBackgroundChangedAgain = false;
    } else {
        likeBtn.style.backgroundImage = "url('../img/alert3.png')"; // 배경 이미지를 변경
        isBackgroundChangedAgain = true;
    }
}

document.getElementById("like").addEventListener("click", likeYou);


//게시물 내용창 닫는 함수
const closePostBtn = document.getElementById("close_button");

function closePost(){
    posting.classList.add("hidden_post");
}

closePostBtn.addEventListener("click", closePost);













