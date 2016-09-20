angular.module('bcDirectives')
    .directive('loading', ['$http' ,function ($http) {
        return {
            restrict: 'A',
            link: function(scope, element) {

            	element.addClass('hide');

                scope.isLoading = function () {
                    return $http.pendingRequests.length > 0;
                };

                scope.$watch(scope.isLoading, function(v) {
                    if(v) {
                    	element.removeClass('hide');
                    	element.addClass('show');
                    } else {
                    	if(element.hasClass('show'))
                    		element.removeClass('show');

                    	element.addClass('hide');
                    }
                });
            }
        };

    }]);