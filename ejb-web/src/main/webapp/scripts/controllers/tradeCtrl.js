angular.module('bcControllers')
	.controller('tradeCtrl', function($scope, $sce, base64Service, restService) {

		restService.tradeController().getCaps().success(function(data) {
			console.log(data);
		});

	});