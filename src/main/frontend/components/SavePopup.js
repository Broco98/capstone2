import React from 'react';

const SavePopup = ({ handleCloseSavePopup, handleConfirmSave }) => {
  return (
    <div className="popup">
      <div className="popup-inner">
        <h2>저장 확인</h2>
        <p>저장하시겠습니까?</p>
        <button onClick={handleConfirmSave}>저장</button>
        <button onClick={handleCloseSavePopup}>닫기</button>
      </div>
    </div>
  );
};

export default SavePopup;

