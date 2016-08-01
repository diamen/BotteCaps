angular.module('bcControllers')
	.controller('newsCtrl', function($scope, restService) {

		$scope.pagination = {
			currentPage: 1,
			totalItems: 0
		};
		
		restService.newsController().getNewsCount().success(function(data) {
			$scope.pagination.totalItems = data;

    		$scope.reload(1);
		});
		
		$scope.reload = function() {
			restService.newsController().getNewsFromPage($scope.pagination.currentPage).success(function(data) {
				$scope.newsarr = data;
			});
		};
		
		$scope.checkLength = function(content) {
			
			var doubleContent = {};
			
			if(content.length > 500) {
				doubleContent.fullContent = content;
				var index = content.indexOf(" ", 500);
				doubleContent.shortContent = content.substring(0, index) + "...";
				doubleContent.expand = true;
			} else {
				doubleContent.shortContent = content;
				doubleContent.fullContent = null;
				doubleContent.expand = false;
			}
			return doubleContent;
		};
		
	});