(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CertificateRequestBatchDeleteController',CertificateRequestBatchDeleteController);

    CertificateRequestBatchDeleteController.$inject = ['$uibModalInstance', 'entity', 'CertificateRequestBatch'];

    function CertificateRequestBatchDeleteController($uibModalInstance, entity, CertificateRequestBatch) {
        var vm = this;

        vm.certificateRequestBatch = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CertificateRequestBatch.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
