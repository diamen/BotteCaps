angular.module('bcControllers')
	.controller('anewsCtrl', function($scope, $http, $location) {
		
		var id = $location.path().substring($location.path().lastIndexOf("/"));
		
		$http.post("./rest/news" + id).success(function(data) {
			$scope.news = data;
		});
		
	});