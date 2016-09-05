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

		var config = {};
		config.yes = { msg: "Czy chcesz edytowac newsa?", invoke: edit };
		config.no = { msg: "Czy chcesz anulowac zmiany?", invoke: function() { $scope.$parent.go('news'); } };
		var modalInstance = modalService.create(config);

		$scope.openModal = function(type) {
			modalInstance.execute(type);
		};

});