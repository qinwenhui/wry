/**
 * 
 */
$(function(){
	//填充数据到页面
	fillData();
	function fillData(){
		//获取管理员数量
		getAdminNumber();
		//获取服务员数量
		getWaiterNumber();
		//获取用户数量
		getUserNumber();
		//获取最新版本app
		getMaxVersion();
	}
	
	function getAdminNumber(){
		$.ajax({
    		url: wryConfig.SERVER_URL+'/api/admin/queryAdminNumber.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			if(data.code == 0){
    				$('#admin_number').text(data.data+"人");
    			}
    			console.log(data);
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
	}
	
	function getWaiterNumber(){
		$.ajax({
    		url: wryConfig.SERVER_URL+'/api/waiter/queryWaiterNumber.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			if(data.code == 0){
    				$('#waiter_number').text(data.data+"人");
    			}
    			console.log(data);
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
	}
	
	function getUserNumber(){
		$.ajax({
    		url: wryConfig.SERVER_URL+'/api/user/queryUserNumber.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			if(data.code == 0){
    				$('#user_number').text(data.data+"人");
    			}
    			console.log(data);
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
	}
	
	function getMaxVersion(){
		$.ajax({
    		url: wryConfig.SERVER_URL+'/api/version/queryMaxVersion.action',
    		dataType: 'json',
    		type: 'get',
    		success: function(data){
    			if(data.code == 0){
    				$('#appName').text(data.data.appName);
    				$('#versionName').text(data.data.versionName);
    				$('#publishTime').text(format(data.data.publishTime));
    				$('#download').attr('href',data.data.download);
    				$('#download').text(data.data.download);
    			}
    			console.log(data);
    		},
    		error: function(data){
    			console.log("请求出错:"+data);
    		}
    	});
	}
	
	
	/*
     * 时间戳转时间
     */
    function add0(m){return m<10?'0'+m:m }
    function format(shijianchuo)
    {
	    //shijianchuo是整数，否则要parseInt转换
	    var time = new Date(shijianchuo);
	    var y = time.getFullYear();
	    var m = time.getMonth()+1;
	    var d = time.getDate();
	    var h = time.getHours();
	    var mm = time.getMinutes();
	    var s = time.getSeconds();
	    return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    }
})