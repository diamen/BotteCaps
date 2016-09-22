angular.module('bcDirectives')
	.directive("uploadfile", function () {
	    return {
	        scope: false,
	        link: function (scope, element, attributes) {
	        	
	        	element.on("click", function() {
	        		element.val("");
	        	});
	        	
	            element.on("change", function (changeEvent) {
	            	
	            	var files = changeEvent.target.files;
	            	
	            	var numberOfFiles = files.length;
	            	scope.files = [];
	            	
	            	var uploadFile = function(file, i) {
	                    var reader = new FileReader();
		                reader.onload = function (loadEvent) {
		                    scope.$apply(function () {
		                    	scope.files[i] = { src: loadEvent.target.result, name: file.name, size: file.size, type: file.type, captext: "", capbrand: "", beer: 0 };
		                    });
		                };
	                	reader.readAsDataURL(file);
	            	};
	            	
	            	for(var i = 0; i < numberOfFiles; i++)
	            		uploadFile(files[i], i);
	            });
	        }
	    };
	});