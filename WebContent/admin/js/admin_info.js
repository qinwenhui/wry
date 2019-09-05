/**
 * 
 */
$(function(){
	//获取sessionStorage中的admin字符串
	var admin = sessionStorage.getItem("admin");
	//将该json字符串转化为admin对象
	admin = JSON.parse(admin);
	//填充用户信息
	$('#infoAdminId').text(admin.id);
	$('#infoAdminUsername').text(admin.username);
	$('#infoAdminIcon').attr('src',wryConfig.SERVER_URL+admin.icon);
	//点击了修改密码按钮
	$('#updateBtn').click(function(){
		$('.update-div').show(200);
	})
	
	//点击了取消按钮
	$('#cancelBtn').click(function(){
		$('.update-div').hide(200);
	})
	
	//点击了修改提交按钮
	$('#updateSubmit').click(function(){
		//先检查表单
		if(checkForm()){
			//验证原密码是否正确
			var id = admin.id;
			var yuanPassword = $('#yuanPassword').val();
			checkPassword('id='+id+'&password='+yuanPassword);
		}
	})
	
	//点击了头像
	$('#infoAdminIcon').click(function(){
		//修改头像
		$('#adminId').val(admin.id);
		updateIcon();
	})
	
	//检查表单的函数
	function checkForm(){
		var yuanPassword = $('#yuanPassword').val();
		var newPassword = $('#newPassword').val();
		var newPassword2 = $('#newPassword2').val();
		if(yuanPassword == '' || newPassword == ''){
			alert("密码不能为空");
			return false;
		}
		if(newPassword != newPassword2){
			alert("两次密码不相同");
			return false;
		}
		return true;
	}
	//检查密码是否正确的请求
	function checkPassword(postData){
		$.ajax({
			url: wryConfig.SERVER_URL+'/api/admin/checkPassword.action',
			type: 'post',
			dataType: 'json',
			data: postData,
			success: function(data){
				if(data.code == 0){
					//原密码正确，进行密码修改
					//进行修改的ajax
					var password = $('#newPassword').val();
					updatePassword('id='+admin.id+'&password='+password);
				}else{
					alert("原密码错误");
				}
				console.log(data);
			},
			error: function(data){
				console.log('请求出错:'+data);
			}
		})
	}
	
	//进行修改密码的请求
	function updatePassword(postData){
		$.ajax({
			url: wryConfig.SERVER_URL+'/api/admin/updatePassword.action',
			type: 'post',
			dataType: 'json',
			data: postData,
			success: function(data){
				if(data.code == 0){
					//修改成功
					alert(data.msg);
					$('.update-div').hide(200);
					$('#yuanPassword').val('');
					$('#newPassword').val('');
					$('#newPassword2').val('');
				}else{
					alert(data.msg);
				}
			},
			error: function(data){
				console.log('请求出错:'+data);
			}
		})
	}
	
	//修改头像的请求
	function updateIcon(){
		//弹出选择文件
		$('#uploadInput').click();
	}
	
	/*
	 * 文件上传变更时
	 */
	$('#uploadInput').change(function(){
		console.log($(this).val())
		if($(this).val()!="") { 
			//选择了文件，进行上传请求
			$.ajax({
	            url: wryConfig.SERVER_URL+'/api/admin/updateIcon.action',
	            type: 'post',
	            dataType: 'json',
	            data: new FormData($('#updateIconForm')[0]),
	            processData: false,
	            contentType: false,
	            success: function(data){
	            	if(data.code == 0){
	            		//修改成功
	            		admin.icon = data.data;
	            		$('#infoAdminIcon').attr('src',wryConfig.SERVER_URL+admin.icon);
	            		$('#IndexAdminIcon').attr('src',wryConfig.SERVER_URL+admin.icon);
	            	}else{
	            		alert(data.msg)
	            	}
	            },
	            error: function(data){
	                console.log("请求修改头像失败"+data);
	            }
	        });
		}
	})
})