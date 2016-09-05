angular.module('bcServices')
	.service("modalService", ['$uibModal', function($uibModal) {
		
		return {
			create: function(config) {
				return {
					execute: function(type) {

					var modal = {};

					if(type === 'NO') {
						modal.msg = config.no.msg;
						modal.invoke = config.no.invoke;
					}

					if(type === 'YES') {
						modal.msg = config.yes.msg;
						modal.invoke = config.yes.invoke;
					}

					var modalInstance = $uibModal.open({
					      animation: true,
					      templateUrl: '/ejb-web/views/templates/modal.html',
					      controller: 'modalCtrl',
					      size: 'sm',
					      resolve: {
					        msg: function () {
					          return modal.msg;
					        }
					      }
					    });

					modalInstance.result.then(function () {
					      modal.invoke();
					    }, function () {
					      console.log('dismissed');
					    });
					}
				};
			}
		};
		
}]);