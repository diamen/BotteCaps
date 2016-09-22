angular.module('bcDirectives')
	.directive("fileread", ['restService', 'base64Service', function (restService, base64Service) {
	    return {
	        scope: {
	            fileread: "="
	        },
	        link: function (scope, element, attributes) {
	            element.bind("change", function (changeEvent) {
	            	
	            	var files = changeEvent.target.files;
	            	var numberOfFiles = files.length;
	            	var i = 0;
	            	
	            	var uploadFile = function(file) {
	                    var reader = new FileReader();
		                reader.onload = function (loadEvent) {
		                    scope.$apply(function () {
		                        scope.fileread = loadEvent.target.result;
		                      
		            			base64Service.imgToBase64(scope.fileread, 'image/jpeg', function(base64) {
		            				restService.adminController().imageUpload(base64).success(function(data) {
		            					if(i + 1 < numberOfFiles) {
		            						i += 1;
		            						uploadFile(files[i]);
		            					}
		            				});
		            			});
		            			
		                    });
		                };
	                	reader.readAsDataURL(file);
	            	};
	            	
	            	uploadFile(files[0]);
	            	
	            });
	        }
	    };
	}]);