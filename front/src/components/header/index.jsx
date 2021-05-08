import React from 'react';
import './index.css';


const CustomHeader = ({title}) => (
    <div className='header-wrapper'>
        <div className='header-wrapper__title-span-container'>
            <span>
                {title}
            </span>
        </div>
        <div className='header-wrapper__notification-container-notification'>
            Входящие
        </div>
        <div className='header-wrapper__notification-container-notification'>
            Исходящие
        </div>
        <div className='header-wrapper__notification-container-notification'>
            Взаимные
        </div>
    </div>
);

export default CustomHeader;