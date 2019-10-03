(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('CertificateRequestBatchDialogController', CertificateRequestBatchDialogController);

    CertificateRequestBatchDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CertificateRequestBatch'];

    function CertificateRequestBatchDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CertificateRequestBatch) {
        var vm = this;

        vm.certificateRequestBatch = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.certificateRequestBatch.id !== null) {
                CertificateRequestBatch.update(vm.certificateRequestBatch, onSaveSuccess, onSaveError);
            } else {
                CertificateRequestBatch.save(vm.certificateRequestBatch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:certificateRequestBatchUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.creationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
