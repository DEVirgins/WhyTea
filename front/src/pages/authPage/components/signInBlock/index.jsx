import React from 'react';
import GoogleIcon from '../../../../assets/svg/googleIcon';
import VkIcon from '../../../../assets/svg/vkIcon';
import './style.css';

const SignInBlock = () => (
    <>
        <div className='signIn-buttons-wrapper'>
            <div className='signIn-buttons-wrapper__auth-container'>
                <div className='signIn-buttons-wrapper__auth-container__icon-wrapper'>
                    <GoogleIcon />
                </div>
                <div className='signIn-buttons-wrapper__auth-container__span-wrapper'>
                    <span>
                        Войти через Google
                    </span>
                </div>
            </div>
            <div className='signIn-buttons-wrapper__auth-container'>
                <div className='signIn-buttons-wrapper__auth-container__icon-wrapper'>
                    <VkIcon />
                </div>
                <div className='signIn-buttons-wrapper__auth-container__span-wrapper'>
                    <span>
                        Войти через ВКонтакте
                    </span>
                </div>
            </div>
        </div>
    </>
);

export default SignInBlock;