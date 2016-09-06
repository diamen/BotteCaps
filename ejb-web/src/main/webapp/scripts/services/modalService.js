angular.module('bcServices')
	.service("modalService", ['$uibModal', function($uibModal) {

		return {
			execute: function(fn, msg) {

			var modalInstance = $uibModal.open({
			      animation: true,
			      templateUrl: '/ejb-web/views/templates/modal.html',
			      controller: 'modalCtrl',
			      size: 'sm',
			      resolve: {
			        msg: function () {
			          return msg;
			        }
			      }
			    });

			modalInstance.result.then(function () {
			      fn();
			    }, function () {
			      console.log('dismissed');
			    });
			}
		};

}]);