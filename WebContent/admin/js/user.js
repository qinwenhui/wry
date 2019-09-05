/**
 * Created by qwh on 2019/5/23.
 */
$(function(){
	//当前页有多少条数据在显示
	var currentNumber = 0;
	//当前选择的条数
	var selectNuumber = 0;
    getAllItemList();
    //设置监听
    setOnClick();
    /*
    	获取用户列表
     */
    function getAllItemList(){
        $.ajax({
            url: wryConfig.SERVER_URL+'/api/user/queryUserAll.action',
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//填充界面列表
            		fillList(data.data)
            		
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        })
    }
    /*
     * 填充列表的函数
     */
    function fillList(data){
    	var trDiv = $('#tr-div')
    	var html = "";
    	for(var i=0;i<data.length;i++){
    		currentNumber++;
    		html += '<tr><td><div class="checkbox"><label><input type="checkbox" id="checkbox'+i+'" value="'+data[i].id+'" aria-label="..."></label></div></td><td>'+data[i].id+'</td><td>'+data[i].username+'</td><td><img src="'
    			+wryConfig.SERVER_URL+data[i].icon+'" width="50px" height="50px"></td><td>'+data[i].nickname+'</td><td>'+data[i].phoneNumber+'</td><td class="notes-td">'+data[i].address+'</td><td>'+format(data[i].registerTime)+'</td><td><button type="button" class="btn btn-default" id="updateBtn'+i+'" value="'+data[i].id+'">详情</button>&nbsp;&nbsp;<button type="button" class="btn btn-default" id="deleteBtn'+i+'" value="'+data[i].id+'">删除</button></td></tr>';
    	}
    	trDiv.html(html);
    	//设置删除和修改的按钮的监听，在这里设置是因为需要列表先刷新出来才能进行删除按钮的监听
    	deleteBtnOnClick();
    	updateBtnOnClick();
    }
    /*
     * 设置监听的函数
     */
    function setOnClick(){
    	//全部物品的按钮
    	$('#allItemBtn').click(function(){
    		//再次获取全部物品填充列表
    		getAllItemList();
    	});
    	//点击模糊查询的按钮
    	$('#seachBtn').click(function(){
    		//获取查询的关键字
    		var keyword = $('#keyword').val();
    		//进行模糊查询
    		$.ajax({
                url: wryConfig.SERVER_URL+'/api/user/queryUserByKeyword.action?keyword='+keyword,
                type: 'get',
                dataType: 'json',
                success: function(data){
                	if(data.code == 0){
                		//填充界面列表
                		fillList(data.data);
                	}else{
                		alert(data.msg);
                	}
                    console.log(data);
                },
                error: function(data){
                    console.log(data);
                }
            })
    	});
    	//点击批量删除
    	$('#selectDeleteBtn').click(function (){
    		if(confirm("确定要删除吗?"))
    	     {
    			var flag = false;
    			selectNuumber = 0;
    			for(var i=0;i<currentNumber;i++){
    	    		//如果当前选择框处于选择状态
    	    		if($('#checkbox'+i).is(':checked')){
    	    			flag = true;
    	    			selectNuumber++;
    	    			break;
    	    		}
    	    	}
    			if(flag){
    				//有选中
    				//执行删除所选的函数
        			deleteSelect();
    			}else{
    				//没选中任何项
    				alert("至少选择一项!")
    			}
    			
    			return true;
    	     }else{
    		   return false;
    		 }
    	});
    	//点击发送通知
    	$('#selectSendNoticeBtn').click(function (){
    		if(confirm("给选中的用户发通知?"))
    	     {
    			var flag = false;
    			selectNuumber = 0;
    			for(var i=0;i<currentNumber;i++){
    	    		//如果当前选择框处于选择状态
    	    		if($('#checkbox'+i).is(':checked')){
    	    			flag = true;
    	    			selectNuumber++;
    	    			break;
    	    		}
    	    	}
    			if(flag){
    				//有选中
    				$('#sendNotice-div').show(200);
    			}else{
    				//没选中任何项
    				alert("至少选择一项!")
    			}
    			return true;
    	     }else{
    		   return false;
    		 }
    	});
    	//点击x图片退出修改
    	$('#cancelImg').click(function(){
    		$('#update-div').hide(200);
    	});
    	$('#cancelImg2').click(function(){
    		$('#sendNotice-div').hide(200);
    	});
    	
    	//点击确认发送通知的按钮
    	$('#sendSubmit').click(function(){
    		//先判断参数是否为空
    		if($('#noticeTitle').val() == ""){
    			alert("标题不能为空")
    			return false;
    		}
    		if($('#noticeContent').val() == ""){
    			alert("内容不能为空")
    			return false;
    		}
    		//批量发送
    		selectSendNotice();
    	});
    }
    
    /*
     * 选择删除
     */
    function deleteSelect(){
    	//循环当前数据的选择框
    	for(var i=0;i<currentNumber;i++){
    		//如果当前选择框处于选择状态
    		if($('#checkbox'+i).is(':checked')){
    			//执行删除操作
    			var id = $('#checkbox'+i).val();
    			deleteOne(id);
    		}
    	}
    }
    /*
     * 批量发送通知
     */
    var sendSuccessCount = 0;
    function selectSendNotice(){
    	//如果当前选择框处于选择状态
    	for(var i=0;i<currentNumber;i++){
			if($('#checkbox'+i).is(':checked')){
				//执行删除操作
				var user_id = $('#checkbox'+i).val();
				sendNotice(user_id);
			}
    	}
    }
    /*
     * 给一个用户发送通知
     */
    function sendNotice(user_id){
    	//获取sessionStorage中的admin字符串
    	var admin = sessionStorage.getItem("admin");
    	//将该json字符串转化为admin对象
    	admin = JSON.parse(admin);
    	$('#adminId').val(admin.id);
    	$('#userId').val(user_id);
    	var postData = $('#sendForm').serialize();
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/notice/addNotice.action',
            type: 'post',
            dataType: 'json',
            data: postData,
            success: function(data){
            	if(data.code == 0){
            		//发送成功
            		sendSuccessCount++;
            		//如果发送成功的条数等于选择的条数说明全部发送成功
            		if(sendSuccessCount == selectNuumber){
            			alert(data.msg)
            			$('#sendNotice-div').hide(200);
            		}
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
    }
    
    /*
     * 删除某一项
     */
    function deleteOne(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/user/deleteUserById.action?id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//删除成功，重新获取物品列表
            		getAllItemList();
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
    }
    
    /*
     * 删除按钮的监听
     */
    function deleteBtnOnClick(){
    	//点击删除按钮,先循环所有的删除按钮
    	for(var i=0;i<currentNumber;i++){
    		$('#deleteBtn'+i).click(function(){
    			//获取这一个物品的id
    			var id = $(this).val();
    			if(confirm("是否删除")){
    				deleteOne(id);
    				return true;
    			}else{
    				return false;
    			}
    			
    		});
    	}
    }
    
    /*
     * 详情按钮的监听
     */
    function updateBtnOnClick(){
    	for(var i=0;i<currentNumber;i++){
    		$('#updateBtn'+i).click(function(){
    			//获取这一个用户的id
    			var id = $(this).val();
    			//弹出修改界面
    			$('#update-div').show(200);
    			//根据id获取用户信息
    			getUserById(id);
    		});
    	}
    }
    
    function getUserById(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/user/queryUser.action?user_id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//查询成功,为用户详情界面填充用户信息
            		fillUserInfo(data.data);
            	}else{
            		alert(data.msg)
            	}
                console.log(data);
            },
            error: function(data){
                console.log(data);
            }
        });
    }
    /*
     * 填充用户详情框的内容
     */
    function fillUserInfo(user){
    	$('#id').text(user.id);
    	$('#username').text(user.username);
    	$('#nickname').text(user.nickname);
    	$('#phoneNumber').text(user.phoneNumber);
    	$('#address').text(user.address);
    	$('#trustNumber').text(user.trustNumber);
    	$('#serviceNumber').text(user.serviceNumber);
    	$('#registerTime').text(format(user.registerTime));
    	$('#credit').text(user.credit);
    	if(user.state == 0){
    		$('#state').text('正常使用');
    	}else{
    		$('#state').text('账号异常');
    	}
    	$('#icon').attr('src',wryConfig.SERVER_URL+user.icon);
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