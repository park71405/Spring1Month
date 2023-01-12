<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
   .uploadResult {
       width: 100%;
       background-color: lightgray;
   }

   .uploadResult ul {
       display: flex;
       flex-flow: row;
       justify-content: center;
       align-items: center;
   }

   .uploadResult ul li {
       list-style: none;
       padding: 10px;
   }

   .uploadResult ul li img {
       width: 100px;
   }
   
   .uploadResult ul li span {color: white;}
   .bigPictureWrapper {
   		position: absolute;
   		display: none;
   		justify-content: center;
   		align-items: center;
   		top: 0%;
   		width: 100%;
   		height: 100%;
   		z-index: 100;
   		background: rgba(255, 255, 255, 0.5);
   }
   .bigPicture {
   		position: relative;
   		display: flex;  //화면의 정중앙 배치
   		justify-content: center;  //가운데 정렬
   		align-items: center;
   }
   .bigPicture img {width: 600px;}
</style>

</head>
<body>

<h1>Upload with Ajax</h1>
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple>
	</div>
	<button id="uploadBtn">Upload</button>

	<div class="uploadResult">
		<ul></ul>
	</div>
	
	<div class='bigPictureWrapper'>
		<div class='bigPicture'></div>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

	<script>
	
		function showImage(fileCallPath){
			 $(".bigPictureWrapper").css("display","flex").show();  //화면 가운데 배치
		        $(".bigPicture")
		        .html("<img src='/display?fileName="+encodeURI(fileCallPath)+"'>")  //<img>추가
		        .animate({width:'100%', height:'100%'}, 1000);
		}
	
		$(document).ready(function() {
			
			//업로드 파일의 확장자와 크기 설정
			let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
			let maxSize = 5242880; //5MB
			
			function checkExtendsion(fileName, fileSize){
				if(fileSize >= maxSize){
					alert("파일 사이즈 초과");
					return false;
				}
				
				if(regex.test(fileName)){
					alert("해당 종류의 파일은 업로드 할 수 없습니다.");
					return false;
				}
				
				return true;
			}
			
			let uploadResult = $(".uploadResult ul");

			
			function showUploadedFile(uploadResultArr) {
			    var str = "";
			    
			    $(uploadResultArr).each(function(i, obj) {
			        // image 파일이 아닌 경우
			        if (!obj.image) {
			            var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
			            
			            var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");
			            
			            str += "<li><div><a href='/download?fileName="+fileCallPath+"'>"
		                + "<img src='/resources/img/attachment.png'>"+obj.fileName+"</a>"
		                + "<span data-file=\'"+fileCallPath+"\' data-type='file'> x </span>"
		                + "<div></li>"

			        } else {
			            var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
			            var originPath = obj.uploadPath + "\\" + obj.uuid + "_" + obj.fileName;
			            originPath = originPath.replace(new RegExp(/\\/g),"/");
			            
			            str += "<li><a href=\"javascript:showImage(\'"+originPath+"\')\">"
			            + "<img src='/display?fileName="+fileCallPath+"'></a>"
			            + "<span data-file=\'"+fileCallPath+"\' data-type='image'> x </span>"
			            + "</li>";
			        }
			    });
			    
			    uploadResult.append(str);
			}
			
			let cloneObj = $(".uploadDiv").clone();
			
			$("#uploadBtn").on("click", function(e) {
				let formData = new FormData();
				let inputFile = $("input[name='uploadFile']");
				let files = inputFile[0].files;
				console.log(files);
				
				for(let i=0; i<files.length; i++){
					
					//확장자와 파일 크기 체크
					if(!checkExtendsion(files[i].name, files[i].size)){
						return false;
					}
					
					formData.append("uploadFile", files[i]);
				}
				
				$.ajax({
					url: '/uploadAjaxAction',
					processData: false, 	//이 두개를 false로 해야 전송됨
					contentType: false,
					data: formData,
					type: 'POST',
					dataType: 'json',
					success: function(result){
						console.log(result);
						showUploadedFile(result);
						$(".uploadDiv").html(cloneObj.html());
					}
				});

			});
			
			$(".bigPictureWrapper").on("click", function(e){
				$(".bigPicture").animate({width:'0%', height:'0%'}, 1000);
				setTimeout(function(){
					$(".bigPictureWrapper").hide();
				}, 1000);
			});
			
			$(".uploadResult").on("click","span",function(e){
			    var targetFile = $(this).data("file");
			    var type = $(this).data("type");
			    console.log(targetFile);
			    
			    $.ajax({
			        url: '/deleteFile',
			        data: {fileName: targetFile, type: type},
			        dataType: 'text',
			        type: 'POST',
			        success: function(result) {
			            alert(result);
			        }
			    });
			});

		});
	</script>

</body>
</html>