import React from "react";
import Button from "react-bootstrap/Button";

const ActionButton = ({onClick, className, children}) => {
  return <Button
      variant={'light'}
      onClick={onClick}
      className={className}>
    {children}
  </Button>

}

export default ActionButton