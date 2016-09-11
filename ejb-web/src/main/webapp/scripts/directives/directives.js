angular.module('bcDirectives', [])

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
	})

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
	}])
	
	.directive("markButton", function() {
		return {
			restrict: 'E',
			link: function(scope, element, attrs) {
				element.css('width', 'inherit');
				
				element.bind("click", function(clickEvent) {
					var btn = element.children();
					if(btn.hasClass("btn-primary")) {
						btn.removeClass("btn-primary");
						btn.addClass("btn-danger");
					} else{
						btn.removeClass("btn-danger");
						btn.addClass("btn-primary");
					}
				});
			},
			template: '<button style="width: inherit" type="submit" class="btn btn-primary">Zaznacz</button>'
		};
	})
	
	.directive('fadeIn', function($timeout) {
		return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	        	element.addClass("invis");
	            element.on('load', function() {
	            	if(element.hasClass("visib")) {
	            		element.removeClass("visib");
	            	}
	            	$timeout(function() {
	            		element.addClass("visib");
	            	}, 50);
	            });
        	}
    	};
	})

	.directive("isAdmin", ['$sessionStorage', '$rootScope', 'authCacheFactory', 'validateFactory', function($sessionStorage, $rootScope, authCacheFactory, validateFactory) {
		return {
			restrict: 'A',
			link: function(scope, element) {

				$rootScope.$watch('$storage.authToken', function() {

					if($sessionStorage.authToken === undefined) {
						element.css('display', 'none');
						return;
					} else {

						if(authCacheFactory.getCache()) {
							element.css('display', 'initial');
							return;
						}

						validateFactory.isValid($sessionStorage.authToken).then(function(data) {
							data ? element.css('display', 'initial') : element.css('display', 'none');
						});

					}

				});

			}
		};
	}]);