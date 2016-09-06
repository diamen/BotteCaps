angular.module('bcControllers')
	.controller('editNewsCtrl', function($scope, $stateParams, modalService, restService) {

		$scope.id = $stateParams.id;

		restService.newsController().getSingleNews($scope.id).success(function(data) {
			$scope.news = data;
		});

		var edit = function() {
			restService.adminController().editNews($scope.news.id, $scope.news.title, $scope.news.content).success(function() {
				$scope.$parent.go('news');
			});
		};

		$scope.openSubmitModal = function() {
			modalService.execute(edit, "Czy chcesz zatwierdzic zmiany?");
		};

		$scope.openCancelModal = function() {
			modalService.execute(function() { $scope.$parent.go('news'); }, "Czy chcesz anulowac zmiany?");
		};

});