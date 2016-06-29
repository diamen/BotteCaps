angular.module('bcDirectives', [])

	.directive("uploadfile", function () {
	    return {
	        scope: false,
	        link: function (scope, element, attributes) {
	            element.bind("change", function (changeEvent) {
	            	
	            	var files = changeEvent.target.files;
	            	var numberOfFiles = files.length;
	            	scope.files = [];
	            	
	            	var uploadFile = function(file, i) {
	                    var reader = new FileReader();
		                reader.onload = function (loadEvent) {
		                    scope.$apply(function () {
		                    	scope.files[i] = { src: loadEvent.target.result, captext: "", capbrand: "", beer: 0 };
		                    });
		                };
	                	reader.readAsDataURL(file);
	            	};
	            	
	            	for(var i = 0; i < numberOfFiles; i++)
	            		uploadFile(files[i], i);
	            	
	            });
	        }
	    };
	})

	.directive("fileread", ['restService', 'base64Service', '$q', function (restService, base64Service, $q) {
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
		            					console.log(data);
		            					if(i + 1 < numberOfFiles) {
		            						i += 1;
		            						uploadFile(files[i]);
		            					}
		            				});
		            			});
		            			
		                    });
		                };
		                console.log(file);
	                	reader.readAsDataURL(file);
	            	};
	            	
	            	uploadFile(files[0]);
	            	
	            });
	        }
	    };
	}])

	.directive("isAdmin", ['$http', '$sessionStorage', '$rootScope', function($http, $sessionStorage, $rootScope) {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
				
				$rootScope.$watch('$storage.authToken', function() {
					
					if($sessionStorage.authToken === undefined) {
						element.css('display', 'none');
						return;
					} else {
						$http({
		    				method: "GET",
		    				url: "./rest/auth/validate",
		    				headers: {'AUTH-TOKEN': $sessionStorage.authToken }
		    				}).success(function(data) {
		    					if(data)
		    						element.css('display', 'initial');
		    					else
		    						element.css('display', 'none');
		    			});	
					}
					
				});

			}
		}
	}]);