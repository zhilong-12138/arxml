$(function(){
	 
	 
	//选择文件
	$('input[id=smwhitelistfile]').change(function() {  
		$('#smwhitelistfileTemp').val($(this).val());  
	});
	
	/*点击导入报警文本按钮*/
	var isSubmit = 1;
	$("#smwhitelist_import").on('click',function(){
		isSubmit = 1;
		$('#mypretime').val(0);
		$('#import_smwhitelist_save').html('导入');
		$('#smwhitelistfile').val('');
		$('#smwhitelistfileTemp').val('');
		$('#importSmWhiteList').modal('show');
	});
	
	/*导入选择的excel中的数据*/
	$("#import_smwhitelist_save").on('click',function(){
		/* 防止重复提交 */
		var Today = new Date();
		var NowHour = Today.getHours();
		var NowMinute = Today.getMinutes();
		var NowSecond = Today.getSeconds();
		var mysec = (NowHour*3600)+(NowMinute*60)+NowSecond;
		var mypretime = $('#mypretime').val();
		//5秒内不允许重复提交
		if((mysec-mypretime) > 5){
			$('#mypretime').val(mypretime);
		}else{
			$.scojs_message('正在导入中，请稍等！', $.scojs_message.TYPE_OK);
			return false;
		}
		if(isSubmit > 1){
			$.scojs_message('正在导入中，请稍等！', $.scojs_message.TYPE_OK);
			return false;
		}
		
		isSubmit = 99;
		//判断选择的文件是否是excel格式
		var alarmTextfile = $('#smwhitelistfile').val();
		if(null == alarmTextfile || '' == alarmTextfile || undefined == alarmTextfile){
			alert('请选择excel文件！');
			isSubmit = 1;
			return false;
		}
		var indx = alarmTextfile.lastIndexOf('.');
		if(indx != -1){
			var ext = alarmTextfile.substr(indx + 1).toUpperCase();
            ext = ext.toLowerCase( );
            if(ext != 'xls' && ext != 'xlsx'){
            	alert('请选择excel文件！');
            	isSubmit = 1;
    			return false;
            }
		}else{
			alert('请选择excel文件！');
			isSubmit = 1;
			return false;
		}
		//判断文件大小
		var fileSize =  document.getElementById('smwhitelistfile').files[0];
		if(fileSize.size > 10485760){
			alert('请上传不超过10M的excel文件！');
			isSubmit = 1;
			return false;
		}
		//
		$('#import_smwhitelist_save').html('导入中...');
		//请求URL
		var ajaxUrl = _ctx + '/jdorder/importBatchlist';
		//提交
		$('#import_smwhitelist_form').ajaxSubmit({
			type : 'POST',
			url : ajaxUrl,
			success : function(res) {
				isSubmit = 1;
				$('#importSmWhiteList').modal('hide');
				$('#import_smwhitelist_save').html('导入');
				$('#smwhitelistfile').val('');
				$('#smwhitelistfileTemp').val('');
				alert(res.msg); 
			},
			error : function(res) {
				isSubmit = 1;
				
				$('#importSmWhiteList').modal('hide');
				$('#import_smwhitelist_save').html('导入');
				$('#smwhitelistfile').val('');
				$('#smwhitelistfileTemp').val(''); 
				alert(res.msg);
		 
			}
		});
	});
});