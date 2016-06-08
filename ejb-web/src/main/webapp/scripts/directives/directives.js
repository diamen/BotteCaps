angular.module('bcDirectives', [])

	.directive("fileread", ['restService', 'base64Service', '$q', function (restService, base64Service, $q) {
	    return {
	        scope: {
	            fileread: "="
	        },
	        link: function (scope, element, attributes) {
	            element.bind("change", function (changeEvent) {
	            	
	            	var asyncRequests = [];
	            	
	                angular.forEach(changeEvent.target.files, function(value, key) {
		                var reader = new FileReader();
		                reader.onload = function (loadEvent) {
		                    scope.$apply(function () {
		                        scope.fileread = loadEvent.target.result;
		                      
		            			base64Service.imgToBase64(scope.fileread, 'image/jpeg', function(base64) {
		            				console.log("BASE64 " + base64);
		            				var promise = restService.adminController().imageUpload(base64);
		            				asyncRequests.push(promise);
		            				console.log("PROMISE " + promise);
		            			});
		            			
		                    });
		                }
	                	reader.readAsDataURL(value);
	                });
	                
	                $q.all(asyncRequests);
	            });
	        }
	    }
	}])

	.directive("isAdmin", ['$http', '$sessionStorage', '$rootScope', function($http, $sessionStorage, $rootScope) {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
				
				$rootScope.$watch('$storage.authToken', function() {
					console.log($rootScope.$storage.authToken);
					
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
	}])

	.directive("photoBox", ['$window', 'randomPhotoService', function($window, randomPhotoService) {
		return {
			link : function(scope, element, attrs) {
				
				angular.element($window).bind('resize', function() {
					resize();
					scope.$apply();
				});
				
				var resize = function() {
					var elemWidth = element[0].offsetWidth - 30;	// 30 - padding
					var photoWidth = Math.floor(0.95 * elemWidth / 3);
					
					var margin = (elemWidth / 3 - photoWidth) / 3 + "px";
					
					var elemHeight = element[0].offsetHeight;
					var photoRowCount = Math.floor(elemHeight / photoWidth);
					var photoCount = photoRowCount * 3;
					
					scope.photoMargin = margin;
					scope.size = photoWidth;
					scope.photos = [];
					
					randomPhotoService.execute(photoCount, updateView);
				}
				
				var updateView = function(photo) {
					scope.photos.push(photo);				
				};
				
				resize();
				
			},
			restrict: "E",
			template: "<div class=\"vmiddle\"><a ng-repeat=\"photo in photos track by $index\">" +
					"<img ng-src=\"{{photo}}\" style=\"margin : {{photoMargin}};\" " +
					"height=\"{{size}}\" width=\"{{size}}\">" +
					"</a></div>"
		}
	}]);