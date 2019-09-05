/**
 * Created by qwh on 2019/5/23.jquery
 */
$(function(){
	//当前页有多少条数据在显示
	var currentNumber = 0;
	//用来临时存要修改的标题和内容
	var title,content;
	
    getAllItemList();
    
    //设置监听
    setOnClick();
    /*
    	获取通知列表
     */
    function getAllItemList(){
    	//用ajax请求后端接口获取所有通知
        $.ajax({
            url: wryConfig.SERVER_URL+'/api/notice/queryNoticeVo.action',
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
                console.log("请求出错"+data);
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
    		html += '<tr><td><div class="checkbox"><label><input type="checkbox" id="checkbox'+i+'" value="'+data[i].id+'" aria-label="..."></label></div></td><td>'+data[i].id+'</td><td>'+data[i].title+'</td><td>'+data[i].content+'</td><td class="notes-td">'+data[i].admin.username+'</td><td>'+data[i].user.username+'</td><td>'+format(data[i].publishTime)+'</td><td><button type="button" class="btn btn-default" id="updateBtn'+i+'" value="'+data[i].id+'">修改</button>&nbsp;&nbsp;<button type="button" class="btn btn-default" id="deleteBtn'+i+'" value="'+data[i].id+'">删除</button></td></tr>';
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
    	//全部通知的按钮
    	$('#allNoticeBtn').click(function(){
    		//再次获取全部通知填充列表
    		getAllItemList();
    	});
    	//点击模糊查询的按钮
    	$('#seachBtn').click(function(){
    		//获取查询的关键字
    		var keyword = $('#keyword').val();
    		//进行模糊查询
    		$.ajax({
                url: wryConfig.SERVER_URL+'/api/notice/queryNoticeVoByKeyword.action?keyword='+keyword,
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
    		//把表单输入框的内容置空
			$('#itemName').val('');
			$('#itemContent').val('');
    	});
    	//点击修改框中的修改按钮
    	$('#updateSubmit').click(function(){
    		//先检查表单中的数据是否符合要求
    		if(checkUpdateForm()){
    			//检查通过,进行修改请求
    			updateHttp($('#updateForm').serialize());
    		}
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
            url: wryConfig.SERVER_URL+'/api/notice/deleteNoticeById.action?id='+id,
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
     * 修改按钮的监听
     */
    function updateBtnOnClick(){
    	for(var i=0;i<currentNumber;i++){
    		$('#updateBtn'+i).click(function(){
    			//获取这一个物品的id
    			var id = $(this).val();
    			//弹出修改界面
    			$('#update-div').show(200);
    			//把修改的物品id传入
    			$('#idInput').val(id);
    			//通过id获取要修改的通知的详情，并显示到修改界面的输入框
    			getNoticeById(id);
    		});
    	}
    }
    
    /*
     * 检查表单数据
     */
    function checkUpdateForm(){
    	if($('#noticeTitle').val() == ''){
    		alert("标题不能为空");
    		return false;
    	}
    	if($('#noticeContent').val() == ''){
    		alert("内容不能为空");
    		return false;
    	}
    	return true;
    }
    
    /*
     * 更新修改的网络请求
     */
    function updateHttp(postData){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/notice/updateNotice.action',
            type: 'post',
            dataType: 'json',
            data: postData,
            success: function(data){
            	if(data.code == 0){
            		//更新成功
            		alert("更新成功");
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
     * 获取某个id的通知信息
     */
    function getNoticeById(id){
    	$.ajax({
            url: wryConfig.SERVER_URL+'/api/notice/queryNoticeById.action?id='+id,
            type: 'get',
            dataType: 'json',
            success: function(data){
            	if(data.code == 0){
            		//获取某个通知成功
            		console.log(data);
            		//填充该通知内容到输入框
            		$('#noticeTitle').val(data.data.title);
            		$('#noticeContent').val(data.data.content);
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