/**
 * 登录
 */
$(function(){
	//用来判断验证码是否正确的变量
	var codeOk = false;
	//先把图片获取到
	$('#codeImg').attr('src',(wryConfig.SERVER_URL+'/other/imageCode.action?time='+new Date()));
	
	$('#code').change(function(){
		var codeStr = $(this).val();
		//请求后台判断验证码是否正确
		checkCode(codeStr);
	});
	
	/*
	 * 点击验证码切换
	 */
	$('#codeImg').click(function(){
		$(this).attr('src',(wryConfig.SERVER_URL+'/other/imageCode.action?time='+new Date()));
	});
	/*
	 * 判断验证码的请求
	 */
	function checkCode(codeStr){
		$.ajax({
			url: wryConfig.SERVER_URL+'/other/checkCode.action?code='+codeStr,
			type: 'get',
			dataType: 'json',
			success: function(data){
				console.log(data)
				if(data.code == 0){
					//验证码正确
					codeOk = true;
					$('#codeMsg').hide();
				}else{
					//验证码错误，显示提示信息
					$('#codeMsg').show();
					codeOk = false;
				}
			},
			error: function(data){
				console.log("请求出错:"+data)
			}
		})
	}
	
	/*
	 * 点击登录按钮
	 */
	$('#loginSubmit').click(function(){
		if(checkForm()){
			//检查表单通过,进行登陆操作
			login($('#loginForm').serialize());
		}
	})
	
	/*
	 * 检查表单
	 */
	function checkForm(){
		var username = $('#username').val();
		var password = $('#password').val();
		if(username == ''){
			alert("账号不能为空");
			return false;
		}
		if(password == ''){
			alert("密码不能为空");
			return false;
		}
		if(!codeOk){
			//验证码不正确
			$('#codeMsg').show();
			return false;
		}
		return true;
	}
	
	/*
	 * 登录请求
	 */
	function login(postData){
		$.ajax({
            url: wryConfig.SERVER_URL+'/api/admin/login.action',
            type: 'post',
            dataType: 'json',
            data: postData,
            success: function(data){
            	if(data.code == 0){
            		//登录成功，跳转到管理首页
            		window.location.href="index.html";
            	}else{
            		alert(data.msg)
            	}
            },
            error: function(data){
                console.log(data);
            }
        });
	}
})