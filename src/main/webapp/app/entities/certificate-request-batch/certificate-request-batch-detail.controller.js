(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CertificateRequestBatchDetailController', CertificateRequestBatchDetailController);

    CertificateRequestBatchDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CertificateRequestBatch'];

    function CertificateRequestBatchDetailController($scope, $rootScope, $stateParams, previousState, entity, CertificateRequestBatch) {
        var vm = this;

        vm.certificateRequestBatch = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jhipsterApp:certificateRequestBatchUpdate', function(event, result) {
            vm.certificateRequestBatch = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
