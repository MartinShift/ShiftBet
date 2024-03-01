function topup() {
    Swal.fire({
        title: 'Enter the amount to deposit',
        input: 'number',
        inputAttributes: {
            autocapitalize: 'off'
        },
        inputValidator: (value) => {
            if (!value) {
                return 'Enter the amount to deposit!';
            }
        },
        showCancelButton: true,
        confirmButtonText: 'Deposit',
        showLoaderOnConfirm: true,
        preConfirm: (amount) => {
            window.location.href="/user/topup?amount=" + amount;
        },
        allowOutsideClick: () => !Swal.isLoading()
    });
}