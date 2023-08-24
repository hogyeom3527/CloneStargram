const fileInput = document.getElementById('fileInput');

fileInput.value = null;

loadProfile();

// 프로필 이미지 불러오기 위한 함수
// DB에서 프로필 이미지 경로를 가져와 setProfileImg의 매개변수로 제공
// getFileSrc()로 파일이 위치한 경로를 받아옴
function loadProfile() {
    fetch('/load/profileImg', {
        method: 'GET',
        credentials: 'include'
    }).then(response => {
        return response.text();
    }).then(data => {
        setProfileImg(data);
    });
}


// setProfile
fileInput.addEventListener('change', () => {
    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append('file', file);


    fetch('/Member/upload/profileImg', {
        method: 'POST',
        body: formData,
        credentials: 'include' // 쿠키 포함하여 업로드
    }).then(response => {
        if(response.ok) {
            return response.text();
        }else {
            throw new Error('파일 업로드 완료되지 못함.')
        }
    }).then(data => {
        console.log("포스트요청 받아옴 : " + data);

        if (data == null) {
            return 0;
        }
        else setProfileImg(data);
    });
});

// ProfileImg DTO를 GET 요청으로 가져와 프로필 사진 경로를 src 값으로 삽입해주는 function
function setProfileImg(src) {
    const userProfile = document.getElementById("profile_photo");
    fetch("/profile/" + src, {
        method: 'GET'
    }).then(response => { return response.blob()}
    ).then(data =>{
        const image_src = URL.createObjectURL(data);
        console.log("바꾼 이미지 src = " + image_src);
        userProfile.style.backgroundImage = "url(" + image_src + ")";


    });

}