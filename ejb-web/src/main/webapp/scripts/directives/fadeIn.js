angular.module('bcDirectives')
	.directive('fadeIn', function($timeout) {
		return {
	        restrict: 'A',
	        link: function(scope, element) {
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
	});