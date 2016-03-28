angular.module('bcDirectives', [])
	.directive("photoBox", function($window) {
		return {
			link : function(scope, element, attrs) {
				
				angular.element($window).bind('resize', function() {
					resize();
					scope.$apply();
				});
				
				var resize = function() {
					var imgSrc = "http://localhost:8080//ejb-web//resources//gfx//albania//1.jpg";
						
					var elemWidth = element[0].offsetWidth - 30;	// 30 - padding
					var photoWidth = Math.floor(0.95 * elemWidth / 3);
					
					var margin = (elemWidth / 3 - photoWidth) / 3 + "px";
					
					var elemHeight = element[0].offsetHeight;
					var photoRowCount = Math.floor(elemHeight / photoWidth);
					var photoCount = photoRowCount * 3;
					
					scope.photoMargin = margin;
					scope.size = photoWidth;
					scope.photos = [];
					
					for(var i = 0; i < photoCount; i++) {
						scope.photos.push(imgSrc);
					}
				}
				
				resize();
				
			},
			restrict: "E",
			template: "<div class=\"vmiddle\"><a ng-repeat=\"photo in photos track by $index\">" +
					"<img ng-src=\"{{photo}}\" style=\"margin : {{photoMargin}};\" " +
					"height=\"{{size}}\" width=\"{{size}}\">" +
					"</a></div>"
		}
	});