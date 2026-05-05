// ===== Auto hide alerts after 4 seconds =====
document.addEventListener('DOMContentLoaded',
    function() {
        // Skip profile page entirely
        // Profile page has its own script
        if (window.location.pathname
                .includes('profile')) {
            return;
        }

        setTimeout(function() {
            const alerts = document
                .querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                alert.style.transition =
                    'opacity 0.5s ease';
                alert.style.opacity = '0';
                setTimeout(function() {
                    alert.style.display = 'none';
                }, 500);
            });
        }, 4000);
    }
);
// ===== Confirm before delete/close =====
function confirmAction(message) {
    return confirm(message ||
        'Are you sure?');
}

// ===== Search input clear button =====
document.addEventListener('DOMContentLoaded',
    function() {
        const searchInput = document
            .getElementById('searchInput');
        if (searchInput) {
            searchInput.addEventListener(
                'input', function() {
                    const clearBtn = document
                        .getElementById('clearSearch');
                    if (clearBtn) {
                        clearBtn.style.display =
                            this.value ? 'block'
                            : 'none';
                    }
                }
            );
        }
    }
);

// ===== Active navbar link highlight =====
document.addEventListener('DOMContentLoaded',
    function() {
        const currentPath =
            window.location.pathname;
        const navLinks = document
            .querySelectorAll('.navbar-nav .nav-link');
        navLinks.forEach(function(link) {
            if (link.getAttribute('href')
                    === currentPath) {
                link.classList.add('active');
            }
        });
    }
);

// ===== Form validation feedback =====
document.addEventListener('DOMContentLoaded',
    function() {
        const forms = document
            .querySelectorAll('form');
        forms.forEach(function(form) {
            form.addEventListener('submit',
                function(e) {
                    const requiredFields =
                        form.querySelectorAll(
                            '[required]');
                    let valid = true;
                    requiredFields.forEach(
                        function(field) {
                            if (!field.value
                                    .trim()) {
                                field.classList
                                    .add(
                                    'is-invalid');
                                valid = false;
                            } else {
                                field.classList
                                    .remove(
                                    'is-invalid');
                            }
                        }
                    );
                }
            );
        });
    }
);

/// ===== Loading button on form submit =====
document.addEventListener('DOMContentLoaded',
    function() {
        const submitBtns = document
            .querySelectorAll(
                'button[type="submit"]');
        submitBtns.forEach(function(btn) {
            btn.addEventListener('click',
                function() {
                    const form = btn.closest('form');

                    // Skip GET forms (search forms)
                    if (!form ||
                        form.method.toLowerCase()
                            === 'get') {
                        return;
                    }

                    // Skip logout forms
                    if (form.action.includes(
                            'logout')) {
                        return;
                    }

                    // Skip close job forms
                    if (form.action.includes(
                            'close')) {
                        return;
                    }

                    // Only show loading for
                    // valid forms
                    if (form.checkValidity()) {
                        const originalText =
                            btn.innerHTML;
                        btn.innerHTML =
                            '⏳ Submitting...';
                        // Re-enable after 5 seconds
                        // in case of error
                        setTimeout(function() {
                            btn.disabled = false;
                            btn.innerHTML =
                                originalText;
                        }, 5000);
                    }
                }
            );
        });
    }
);