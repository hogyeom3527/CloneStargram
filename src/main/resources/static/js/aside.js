
//만들기 창을 여는 함수
const openBtn = document.getElementById("make");

function openMaking(){
    making.classList.remove("hidden");
}

openBtn.addEventListener("click", openMaking);


//만들기 창을 닫는 함수
const closeBtn = document.getElementById("close_make");
const making = document.querySelector(".make_modal");

function closeMaking(){
    making.classList.add("hidden");
}

closeBtn.addEventListener("click", closeMaking);


//선택된 메뉴의 이미지를 바꾸는 함수
const makeURL = "url('../img/make.png')"
const makeURL2 = "url('../img/make2.png')"
const makeImg = document.querySelector(".make_img");

function changeImg(){
    makeImg.style.backgroundImage = makeURL2;
}
function chageImgAgain(){
    makeImg.style.backgroundImage = makeURL;
}
openBtn.addEventListener("click",changeImg);
closeBtn.addEventListener("click", chageImgAgain);
making.addEventListener("click", chageImgAgain);


//다음 만들기 창을 띄우는 함수
const chooseBtn = document.getElementById("choose");
const makingAgain = document.querySelector(".plus_modal")

function openAgain(){
    makingAgain.classList.remove("hiddenAgain");
}

function closeOpen(){
    closeMaking();
    openAgain();
}
// 해당 기능은 이미지가 불러와진 이후에 진행되어야 합니다.
// chooseBtn.addEventListener("click", closeOpen);


//다음 창에서 이전으로 가는 함수
const backBtn = document.getElementById("back");

function closeAgain(){
    makingAgain.classList.add("hiddenAgain");
}
function back(){
    closeAgain();
    openMaking();
}
backBtn.addEventListener("click",back);


//게시물 설명 글자수 제한 함수
function limit(){
    const maxLength = parseInt(writeArea.getAttribute('maxlength'));

    if(charCount<=maxLength){
        charCountElement.textContent = charCount;
    }
    else{
        writeArea.value = writeArea.value.substr(0, maxLength);
    }
}

//게시물 설명 글자수 세는 함수
const writeArea = document.getElementById("write_post");
const charCountElement = document.getElementById("char_count");

function count(){
    const charCount = writeArea.value.length;
    charCountElement.textContent = charCount;
}

//제한 및 카운드 실행
function limitAndCount(){
    limit();
    count();
}
writeArea.addEventListener('input',count);


//게시물 삭제 안내창 여는 함수
const closeAgainBtn = document.getElementById("close_again");
const postAlert = document.querySelector(".post_alert");

function openAlert(){
    postAlert.classList.remove("hiddenAlert");
}

closeAgainBtn.addEventListener("click",openAlert);


//게시물 삭제 안내창 취소 함수
const cancelAlert = document.getElementById("cancel");

function closeAlert(){
    postAlert.classList.add("hiddenAlert");
}

cancelAlert.addEventListener("click",closeAlert);


//게시물 삭제 함수
const deletePost = document.getElementById("delete");

function closeAll(){
    closeAgain();
    closeAlert();
}
deletePost.addEventListener("click",closeAll);

//게시물 업로드 후 창 닫힘
const shareBtn = document.getElementById("share");

shareBtn.addEventListener("click",closeAll);

