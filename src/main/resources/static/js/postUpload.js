// 1. 이미지 미리보기 제공
// 2. 이미지 및 텍스트 업로드 기능 제공
// 3. 업로드 취소 시 프론트엔드단에 업로드되어있는 이미지 저장 취소



// 게시글 작성 - 이미지 업로드 부문
const postImageInput = document.getElementById("postImageInput");

postImageInput.addEventListener('change', () => {
    const files = postImageInput.files; // 업로드되어있는 파일의 리스트 가져옴

    const thumbnailImg = files[0];
    const imgTag = document.getElementById("imgPreView");
    imgTag.src = URL.createObjectURL(thumbnailImg);
    closeOpen();
});




// 게시글 작성 - 게시글 상세내용 작성 부문

// 공유하기 버튼
const button = document.getElementById("share");

// 공유하기 버튼(== 게시글 업로드 버튼)이 눌린다면...
button.addEventListener('click', () => {
    const postValue = document.getElementById("write_post").value;

            // 파일업로드에 업로드되어있는 파일들을 가져오도록 한다.
    const postImageFiles = postImageInput.files;

    const formData = new FormData();

    if(postImageFiles.length > 0) {
        formData.append('postValue', postValue);
        for(let i = 0; i< postImageFiles.length; i++) {
            formData.append('images', postImageFiles[i]);
        }
    }

    fetch('/Member/postTesting', {
        method: 'POST',
        body: formData,
        credentials: 'include' // 쿠키 포함하여 업로드
    });

    closeMaking(); // 구동안됨... 모달창 닫는 방법 찾기
    
    // 모달창 닫고 iframe을 새로고침 하는 것으로 포스팅 된 즉시 게시글 추가되게끔 할 것
    // 여러 개의 게시글 확인 위해 iframe에 스크롤 기능 추가필요

            
});


