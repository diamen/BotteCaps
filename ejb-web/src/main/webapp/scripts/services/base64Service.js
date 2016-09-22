angular.module('bcServices')
	.service("base64Service", [function() {
		return {
			imgToBase64: function(url, outputFormat, callback) {
			    var img = new Image();
			    img.crossOrigin = 'Anonymous';
			    img.onload = function(){
			        var canvas = document.createElement('CANVAS');
			        var ctx = canvas.getContext('2d');
			        var dataURL;
			        canvas.height = this.height;
			        canvas.width = this.width;
			        ctx.drawImage(this, 0, 0);
			        dataURL = canvas.toDataURL(outputFormat, 1);
			        callback(dataURL);
			        canvas = null; 
			    };
			    img.src = url;
			},
			
			base64ToUrl: function(base64) {
	    		var byteCharacters = atob(base64);
	    		var byteNumbers = new Array(byteCharacters.length);
	    		
	    		for (var i = 0; i < byteCharacters.length; i++) {
	    		    byteNumbers[i] = byteCharacters.charCodeAt(i);
	    		}
	    		
	    		var byteArray = new Uint8Array(byteNumbers);
	    		
	    		var urlCreator = window.URL || window.webkitURL;
	    		return urlCreator.createObjectURL( new Blob( [byteArray] , { type: "image/jpeg" }) );
			}
		}
	}]);