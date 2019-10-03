(function() {
    'use strict';

    angular
        .module('jhipsterApp')
        .controller('FooDialogController', FooDialogController);

    FooDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Foo'];

    function FooDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Foo) {
        var vm = this;

        vm.foo = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.foo.id !== null) {
                Foo.update(vm.foo, onSaveSuccess, onSaveError);
            } else {
                Foo.save(vm.foo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jhipsterApp:fooUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
