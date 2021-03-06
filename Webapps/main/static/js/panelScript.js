var ctx = demo.getContext('2d'),
            w = demo.width,
            h = demo.height,
            px = 0, opx = 0, speed = 5,
            py = h * .5, opy = py,
            scanBarWidth = 20,
                bpmList=[57,60,61,60,61,61,59,58,57,57,58,60,60,60,59,58,57,56,55,55,55,53,52,51,50,],
                hrList=[3,4,5,6,7.1,6,5,4,3,4,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,6,7,6,4,2,5,5,5,6,6,6,6,7,7.1,7,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5],
                diaList=[70,71,72,72,74,74,72,75,76,77,78,80,81,83,84,89,90,93,97,98,100],
                sysList=[110,112,114,118,120,125,127,130,135,135,140,140,145,150,160,163],
                oxList=[97,96,95,95,95,94,93,92,91,90,89,88,87],
                beats=0;
        // for(i=0;i<10;i++){
                // for(j=3;j<8;j++){
                        // hrList[hrList.length]=j*h*.1;// .5*h = middle
                // }
        // }
ctx.strokeStyle = '#00bd00';
ctx.lineWidth = 3;

loop();
var hrStampOld= Date.now();

var a=0,b=0,c=0;
var diaRate=0,sysRate=0,oxRate=0;
function hrAlert(){
	//window.alert("HEART RATE TOO LOW");
}
function bploop(){
	if(a==diaList.length){
		a=0;
	}
	if(b==sysList.length){
		b=0;
	}
	if(c==oxList.length){
		c=0;
	}
	var dia=document.getElementById('dia');
	var sys=document.getElementById('sys');
	var oxim=document.getElementById('oxim');
	
	
	diaRate=diaList[a];
	sysRate=sysList[b];
	oxRate=oxList[c];
	
	dia.innerHTML=diaRate;
	sys.innerHTML=sysRate;
	oxim.innerHTML=oxRate;
	
	if(sysRate<120){
		sys.style.color="#19A319";
	}else if(120<=sysRate && sysRate<140){
		sys.style.color='#F5F118';
	}else if(140<=sysRate && sysRate<159){
		sys.style.color='#FC790D';
	}else{
		sys.style.color='#FC0D0D';
	}
	
	if(diaRate<=80){
		dia.style.color="#46b1f9";
	}else if(80<diaRate && diaRate<=90){
		dia.style.color='#F4FC00';
	}else if(90<diaRate && diaRate<=99){
		dia.style.color='#FCA000';
	}else{
		dia.style.color='#FC0D0D';
	}
	
	if(96<=oxRate && oxRate<=99){
		oxim.style.color="#CC66FF";
	}else if(90<=oxRate && oxRate<96){
		oxim.style.color="#FFFF00";
	}else{
		oxim.style.color='#CC0000';
	}
	a++;
	b++;
	c++;
    
}
var i=0,j=0,k=0;

function loop() {
        if(j==bpmList.length){
                        j=0;
        }
        var hr = document.getElementById('hr');
        
		hr.innerHTML=bpmList[j]+4;
        k=bpmList[j]+4;
		if (k<60){
			
			hr.innerHTML=bpmList[j]+4;
			hr.style.color="red";
			hrAlert();
		}else{
			hr.innerHTML=bpmList[j]+4;
			hr.style.color="#00bd00"
		}
       
        if(i==hrList.length){
                        i=0;
        }
        py=hrList[i]*h*.1;
        px += speed;
   
    ctx.clearRect(px,0, scanBarWidth, h);
    ctx.beginPath();
       
    ctx.moveTo(opx, opy);
    ctx.lineTo(px, py);
    ctx.stroke();
   
    opx = px;
    opy = py;
   
    if (opx > w) {
        px = opx = -speed;
    }
        if(hrList[i]==7.1){
                j++;
                bploop();
        }
    i++;
    
       
    requestAnimationFrame(loop);
}