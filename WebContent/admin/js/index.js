/**
 * Created by admin1 on 2019/5/21.
 */
$(function(){

	//先判断用户是否登录
	getAdminInfo();
	//执行获取ip地址的方法
	getIpAddress();
	//获取当前时间并显示
	showCurrentTime();
    showAndHide('#IndexAdminIcon');
    onClickMenu();
    $('.icon-hover-div').hover(
        function(){
            $('.icon-hover-div').css('display','block');
        },function(){
            setTimeout(function(){
                $('.icon-hover-div').css('display','none');
            },100);
        }
    )

    /*
     * 选中某个菜单项时
     */
    function selectItem(id,path){
    	//先把菜单列表的所有选项的选中效果移除
    	$('li').each(function(){
    		$(this).css('background','#5c5c5c');
    	})
        var item = $(id);
        var html = $('#content-div');
        item.css('background','rgba(100, 101, 100, 0.86)');
        html.load(path);
    }
    //默认选中后台首页选项
    selectItem('#main_admin','index_show.html');

    function showAndHide(id){
        $(id).hover(
            function(){
                setTimeout(function(){
                    $(".icon-hover-div").fadeIn(200);
                },100);
            },function(){
                $('.icon-hover-div').css('display','none');
            }
        )
    }

    function onClickMenu(){
        //点击菜单时
    	$('#main_admin').click(function(){
    		//点击首页
    		selectItem('#main_admin','index_show.html');
    	})
    	$('#user_admin').click(function(){
    		//点击用户管理
    		selectItem('#user_admin','user.html');
    	})
    	$('#waiter_admin').click(function(){
    		//点击服务员管理
    		selectItem('#waiter_admin','waiter.html');
    	})
    	$('#item_admin').click(function(){
    		//点击物品管理
    		selectItem('#item_admin','trustItem.html');
    	})
    	$('#kind_admin').click(function(){
    		//点击类型管理
    		selectItem('#kind_admin','itemKind.html');
    	})
    	$('#trust_order_admin').click(function(){
    		//点击托管订单管理
    		selectItem('#trust_order_admin','trust_order.html');
    	})
    	$('#housework_order_admin').click(function(){
    		//点击家务订单管理
    		selectItem('#housework_order_admin','housework_order.html');
    	})
    	$('#trust_order_review_admin').click(function(){
    		//点击托管评论管理
    		selectItem('#trust_order_review_admin','trust_review.html');
    	})
    	$('#housework_order_review_admin').click(function(){
    		//点击家务评论管理
    		selectItem('#housework_order_review_admin','housework_review.html');
    	})
    	$('#feedback_admin').click(function(){
    		//点击反馈管理
    		selectItem('#feedback_admin','feedback.html');
    	})
    	$('#notice_admin').click(function(){
    		//点击通知管理
    		selectItem('#notice_admin','notice.html');
    	})
    	$('#version_admin').click(function(){
    		//点击版本管理
    		selectItem('#version_admin','version.html');
    	})
    	$('#add_admin').click(function(){
    		//点击添加管理员
    		selectItem('#add_admin','add_admin.html');
    	})
    }

    var user_admin = $('#user-ul-li-ul');
    var waiter_admin = $('#waiter-ul-li-ul');
    var trust_admin = $('#trust-ul-li-ul');

    function showMenu(id){
        var item = $(id);
        if(item.css('display') == 'none'){
            item.show(200);
        }else{
            item.hide(200);
        }
    }
    
    
    
    /*
     * 获取用户信息,检查登录
     */
    function getAdminInfo(){
    	$.ajax({
    		url: wryConfig.SERVER_URL+'/api/admin/checkLogin.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			if(data.code == 0){
    				//已经登录,填充用户信息
    				$('#IndexAdminIcon').attr('src',wryConfig.SERVER_URL+data.data.icon);
    				$('#IndexAdminUsername').text(data.data.username);
    				//把登录信息保存到sessionStorage
    				var admin = JSON.stringify(data.data);
    				sessionStorage.setItem('admin',admin);
    			}else{
    				//暂未登录，跳转到登录界面
    				alert(data.msg);
    				window.location.href="login.html";
    			}
    			console.log(data);
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
    }
    
    /*
     * 获取ip地址
     */
    function getIpAddress(){
    	$.ajax({
    		url: wryConfig.SERVER_URL+'/api/admin/queryClientIp.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			$('#ip').text(data.data);
    			console.log(data);
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
    }
    
    /*
     * 点击个人信息跳转到管理员信息
     */
    $('#adminInfo').click(function(){
    	$('#content-div').load('admin_info.html');
    });
    
    /*
     * 点击注销登录
     */
    $('#cleanLogin').click(function(){
    	//请求注销登录
    	$.ajax({
    		url: wryConfig.SERVER_URL+'/api/admin/cleanLogin.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			//注销成功,刷新页面
    			window.location.href="";
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
    });
    
    /*
     * 获取当前时间并显示
     */
    function showCurrentTime(){
    	setInterval(function(){
    		var date = new Date();
            var seperator1 = "-";
            var seperator2 = ":";
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                    + " " + date.getHours() + seperator2 + date.getMinutes()
                    + seperator2 + date.getSeconds();
        	$('#time').text(currentdate);
    	},1000);
    }
})