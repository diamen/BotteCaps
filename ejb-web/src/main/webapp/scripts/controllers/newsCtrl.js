angular.module('bcControllers')
	.controller('newsCtrl', function($scope, $http, $location) {
		
		$http.get("./rest/news/count").success(function(data) {
			$scope.pagenumbers = Math.ceil(data/10);
			$scope.newscount = new Array($scope.pagenumbers);
			
			if(data > 10) {
				pageReload(1);
			}
			
		});
		
		function pageReload(no) {
			$http.get("./rest/news/page/" + no).success(function(data) {
				$scope.newsarr = data;
				$scope.currentpage = no;
			});
		}
		
		$scope.currentpage = 1;
		
		$scope.pageReload = pageReload;
		
		$scope.nextPage = function() {
			if($scope.currentpage < $scope.pagenumbers) {
				$scope.currentpage += 1;
				$scope.pageReload($scope.currentpage);
			}
		};
		
		$scope.previousPage = function() {
			if($scope.currentpage > 1) {
				$scope.currentpage -= 1;
				$scope.pageReload($scope.currentpage);
			}
		};
		
		$scope.checkLength = function(content) {
			
			var doubleContent = {};
			
			if(content.length > 100) {
				doubleContent.fullContent = content;
				var index = content.indexOf(" ", 100);
				doubleContent.shortContent = content.substring(0, index) + "...";
				doubleContent.expand = true;
			} else {
				doubleContent.shortContent = content;
				doubleContent.fullContent = null;
				doubleContent.expand = false;
			}
			return doubleContent;
		};
		
		$scope.openNews = function(id) {
			$location.path('/news/' + id);
		};
		
	});