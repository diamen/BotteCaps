angular.module('bcControllers')
	.controller('anewsCtrl', function($scope, $location, restService) {
		
		var id = $location.path().substring($location.path().lastIndexOf("/"));
		
		restService.newsController().getSingleNews(id).success(function(data) {
			$scope.news = data;
		});
		
	});