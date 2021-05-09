import React from 'react';
import './index.css';

const PhotoCard = ({ name, teamRole, photoLink }) => (
    <div className='photo-card__wrapper'>
        <div>
            <img src={photoLink}/>
        </div>
        <div className='photo-card-span-wrapper'>
                {name}
        </div>
        <div className='photo-card-span-wrapper'>
            {teamRole}
        </div>
    </div>
);

export default PhotoCard;