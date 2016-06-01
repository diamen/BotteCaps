angular.module('bcControllers')
	.controller('loginCtrl', function($scope, $http, $sessionStorage, $location, $rootScope) {
		
		$http.get("./admin/login").success(function(data, status, headers, config, statusText) {
			$scope.csrfPreventionSalt = headers()['xsrf-token'];
		});
		
		$scope.login = function(j_username, j_password) {
			$http({
			    method: 'POST',
			    url: '/ejb-web/rest/auth/login',
			    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			    transformResponse: function (data, headersGetter, status) {
			        return {data: data};
			    },
			    transformRequest: function(obj) {
			        var str = [];
			        for(var p in obj)
			        	str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
			        return str.join("&");
			    },
			    data: { username: j_username, password: j_password, csrfPreventionSalt: $scope.csrfPreventionSalt }
			}).success(function (data) {
				$rootScope.$storage.authToken = data.data;
				$location.path('/admin/secure');
			});
		}
		
	});