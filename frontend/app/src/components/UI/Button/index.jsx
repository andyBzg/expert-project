import s from './style.module.css'

// variant:
// outlined
// clear
// primary, 
// secondary,

//rounded: rounded
//uppercase: uppercase

//sizes: size_m, size_l, size_xl

const Button = ({
        children,
        variant,
        size,
        style,
        width,
        uppercase,
        rounded,
        ...otherProps
    }) => {
    return (
        <button
            {...otherProps}
            className={
                [s.btn, s[variant], s[size], s[style], s[rounded], s[uppercase]].join(' ')
            }
            width={width}
        >
            {children}
        </button>
    );
};

export default Button;