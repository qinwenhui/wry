/**
 * Created by qwh on 2019/5/23.
 */
$(function(){
	//当前页有多少条数据在显示
	var currentNumber = 0;
	
    getAllItemList();
    //设置监听
    setOnClick();
    /*
    	获取列表
     */
    function getAllItemList(){
        $.ajax({
            url: wryConfig.SERVER_URL+'/api/waiter/queryAllWaiter.action',
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
    			+wryConfig.SERVER_URL+data[i].icon+'" width="50px" height="50px"></td><td>'+data[i].nickname+'</td><td>'+data[i].phoneNumber+'</td><td class="notes-td">'+data[i].address+'</td><td>'+format(data[i].workTime)+'</td><td><button type="button" class="btn btn-default" id="updateBtn'+i+'" value="'+data[i].id+'">详情</button>&nbsp;&nbsp;<button type="button" class="btn btn-default" id="deleteBtn'+i+'" value="'+data[i].id+'">删除</button></td></tr>';
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
                url: wryConfig.SERVER_URL+'/api/waiter/queryWaiterByKeyword.action?keyword='+keyword,
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
    			//执行删除所选的函数
    			deleteSelect();
    			return true;
    	     }else{
    		   return false;
    		 }
    	});
    	//点击x图片退出修改
    	$('#cancelImg').click(function(){
    		$('#update-div').hide(200);
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
     * 删除某一项
     */
    function deleteOne(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/waiter/deleteWaiterById.action?id='+id,
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
    			getWaiterById(id);
    		});
    	}
    }
    /*
     *添加服务员的按钮
     */
    $('#addWaiterBtn').click(function(){
    	$('#add-div').show(200);
    })
    /*
     *取消添加服务员的按钮
     */
    $('#cancelBtn').click(function(){
    	cancelAddDiv();
    })
    /*
     * 添加服务员
     */
    $('#AddSubmit').click(function(){
    	addWaiterHttp(new FormData($("#addWaiterForm")[0]));
    })
    function getWaiterById(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/waiter/queryWaiterById.action?id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//查询成功,为用户详情界面填充用户信息
            		fillWaiterInfo(data.data);
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
     * 添加服务员
     */
    function addWaiterHttp(postData){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/waiter/addWaiter.action',
            type: 'post',
            dataType: 'json',
            data: postData,
            processData: false,
            contentType: false,
            success: function(data){
            	if(data.code == 0){
            		//添加成功
            		alert(data.msg)
            		cancelAddDiv();
            		//重新获取列表
            		getAllItemList();
            	}else{
            		alert(data.msg)
            	}
            },
            error: function(data){
                console.log(data);
            }
        });
    }
    /*
     * 填充用户详情框的内容
     */
    function fillWaiterInfo(waiter){
    	$('#id').text(waiter.id);
    	$('#username').text(waiter.username);
    	$('#nickname').text(waiter.nickname);
    	$('#sex').text(waiter.sex);
    	$('#age').text(waiter.age);
    	$('#education').text(waiter.education);
    	$('#phoneNumber').text(waiter.phoneNumber);
    	$('#address').text(waiter.address);
    	$('#serviceNumber').text(waiter.serviceNumber);
    	$('#workTime').text(format(waiter.workTime));
    	$('#satisfaction').text(waiter.satisfaction);
    	$('#profiles').text(waiter.profiles);
    	if(waiter.state == 0){
    		$('#state').text('正常工作');
    	}else{
    		$('#state').text('已辞职');
    	}
    	$('#icon').attr('src',wryConfig.SERVER_URL+waiter.icon);
    }
    
    /*
     * 取消添加服务员的框框时执行的方法
     */
    function cancelAddDiv(){
    	$('#add-div').hide(200);
    	//去掉输入框的内容
		$('#addWaiterForm').find("input").val('');
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