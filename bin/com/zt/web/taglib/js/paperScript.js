	
	function paper_checkInput(bid,mv){
		var el=document.getElementById(bid);
		var r=new RegExp("^\\s*(\\d+)\\s*$");
		if(r.test(el.value)){
			if(RegExp.$1<1||RegExp.$1>mv){
			alert("页索引超出范围！");
			el.focus(); el.select();
			return false;
			}
		return true;
		}
		alert("页索引不是有效的数值！");
		el.focus();el.select();
		return false;
	}
	
	function paper_keydown(e,btnId){
	var kcode;
	if(window.event){kcode=e.keyCode;}
	else if(e.which){kcode=e.which;}
	var validKey=(kcode==8||kcode==46||kcode==37||kcode==39||(kcode>=48&&kcode<=57)||(kcode>=96&&kcode<=105));
	if(!validKey){
	if(kcode==13) document.getElementById(btnId).click();
	if(e.preventDefault) e.preventDefault();else{event.returnValue=false};
	}
	}
	
	function paper_submit(pIndex)
	{
		document.getElementById("paperIndex").value=pIndex;
		document.forms["paper_form"].submit();
	}
