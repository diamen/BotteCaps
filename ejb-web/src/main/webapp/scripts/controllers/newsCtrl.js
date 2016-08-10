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
		
		$scope.toggleExpand = function(id) {
			$scope.newsarr[id].expanded = !$scope.newsarr[id].expanded;
		};
		
		$scope.isExpanded = function(id) {
			var news = $scope.newsarr[id];
			
			if(!news.hasOwnProperty('expanded'))
				news.expanded = false;
			
			return news.expanded;
		};
		
		$scope.initExpand = function(id) {
			var content = $scope.newsarr[id].content;
			var doubleContent = {};
			
			if(content.length > 500) {
				doubleContent.fullContent = content;
				var index = content.indexOf(" ", 500);
				doubleContent.shortContent = content.substring(0, index) + "...";
				doubleContent.wordy = true;
			} else {
				doubleContent.shortContent = content;
				doubleContent.fullContent = null;
				doubleContent.wordy = false;
			}
			$scope.newsarr[id].doubleContent = doubleContent;
		};
		
		/* add cap */
		$scope.submit = function(news) {
			restService.adminController().addNews(news.title, news.content).success(function(data) {
				console.log(data);
			});
		};
		
	});