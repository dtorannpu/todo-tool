'use client'

import { useState, ReactNode } from "react";

interface ModalProps {
    buttonLabel: string
};

const Modal = ({ buttonLabel, children }: ModalProps & { children: ReactNode }) => {
    const [isOpen, setIsOpen] = useState(false);

    const toggleModal = () => {
        setIsOpen(!isOpen);
    };

    return (
        <>
            <button onClick={toggleModal} className='bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded'>{buttonLabel}</button>

            {isOpen && (
                <div>
                    <div>
                        <div>
                            {children}
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};

export default Modal;
