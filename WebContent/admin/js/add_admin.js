/**
 * 注册管理员
 */

$(function(){
	//监听
	setOnClick();
	
	function setOnClick(){
		//点击注册按钮
		$('#RegisterSubmit').click(function(){
			if(checkForm()){
				//进行注册请求
				register($('#RegisterForm').serialize());
			}
		})
	}
	/*
	 * 验证表单
	 */
	function checkForm(){
		var username = $('#add_username').val();
		var password = $('#add_password').val();
		var password2 = $('#add_password2').val();
		if(username == ''){
			alert("用户名不能为空");
			return false;
		}
		if(password == ''){
			alert("密码不能为空");
			return false;
		}
		if(password != password2){
			alert("两次密码不相同");
			return false;
		}
		return true;
	}
	
	/*
	 * 请求注册的方法
	 */
	function register(postData){
		$.ajax({
			url: wryConfig.SERVER_URL+'/api/admin/register.action',
			dataType: 'json',
			type: 'post',
			data: postData,
			success: function(data){
				console.log(data);
				alert(data.msg);
			},
			error: function(data){
				console.log('请求失败'+data);
			}
		})
	}
})
