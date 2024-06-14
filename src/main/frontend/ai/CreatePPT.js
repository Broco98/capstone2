import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import PptxGenJS from 'pptxgenjs';
import Modal from 'react-modal';
import './CreatePPT.css';

Modal.setAppElement('#root');  

function CreatePPT() {
  const location = useLocation();
  const { formData, textAreas } = location.state;
  const [selectedLayout, setSelectedLayout] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [previewContent, setPreviewContent] = useState(null);

  const formatDate = (date) => {
    if (!date) return '';
    const d = new Date(date);
    return `${d.getFullYear()}/${d.getMonth() + 1}/${d.getDate()}`;
  };

  const generatePreviewContent1 = () => {
    return (
      <div className="slide-preview">
        <h3>{formData.title}</h3>
        <p>Duration: {formatDate(formData.durationStart)} - {formatDate(formData.durationEnd)}</p>
        <p>Name: {formData.name}</p>
        <p>Technology: {formData.technology}</p>
        <p>Summary: {formData.summary}</p>
      </div>
    );
  };

  const generatePreviewContent2 = () => {
    return (
      <div className="slide-preview" style={{ backgroundColor: '#f9f9f9', padding: '20px', borderRadius: '10px' }}>
        <h3 style={{ color: '#ff5733' }}>{formData.title}</h3>
        <p><strong>Duration:</strong> {formatDate(formData.durationStart)} - {formatDate(formData.durationEnd)}</p>
        <p><strong>Name:</strong> {formData.name}</p>
        <p><strong>Technology:</strong> {formData.technology}</p>
        <p><strong>Summary:</strong> {formData.summary}</p>
      </div>
    );
  };

  const generatePPTLayout1 = () => {
    const pptx = new PptxGenJS();
    let slide = pptx.addSlide();
    slide.background = { color: 'f1f1f1' };
    slide.addText(formData.title, { x: 1, y: 1, fontSize: 24, bold: true, color: '007bff' });
    slide.addText(`Duration: ${formatDate(formData.durationStart)} - ${formatDate(formData.durationEnd)}`, { x: 1, y: 2, fontSize: 18 });
    slide.addText(`Name: ${formData.name}`, { x: 1, y: 3, fontSize: 18 });
    slide.addText(`Technology: ${formData.technology}`, { x: 1, y: 4, fontSize: 18 });
    slide.addText(`Summary: ${formData.summary}`, { x: 1, y: 5, fontSize: 18 });

    textAreas.forEach((group) => {
      group.areas.forEach((area) => {
        let slide = pptx.addSlide();
        slide.background = { color: 'ffffff' };
        if (area.type === 'text') {
          slide.addText(area.label, { x: 1, y: 0.5, fontSize: 20, bold: true });
          slide.addText(area.text, { x: 1, y: 1.5, fontSize: 18 });
        } else if (area.type === 'code') {
          slide.addText(area.label, { x: 1, y: 0.5, fontSize: 20, bold: true });
          slide.addText(area.text, { x: 1, y: 1.5, fontSize: 18, fontFace: 'Courier New' });
        } else if (area.type === 'image' && area.preview) {
          slide.addImage({ data: area.preview, x: 1, y: 1, w: 6, h: 4 });
        }
      });
    });

    return pptx;
  };

  const generatePPTLayout2 = () => {
    const pptx = new PptxGenJS();
    let slide = pptx.addSlide();
    slide.background = { color: 'e1e1e1' };
    slide.addText(formData.title, { x: 0.5, y: 0.5, fontSize: 30, bold: true, color: 'ff5733' });
    slide.addText(`Duration: ${formatDate(formData.durationStart)} - ${formatDate(formData.durationEnd)}`, { x: 0.5, y: 1.5, fontSize: 18 });
    slide.addText(`Name: ${formData.name}`, { x: 0.5, y: 2.5, fontSize: 18 });
    slide.addText(`Technology: ${formData.technology}`, { x: 0.5, y: 3.5, fontSize: 18 });
    slide.addText(`Summary: ${formData.summary}`, { x: 0.5, y: 4.5, fontSize: 18 });

    textAreas.forEach((group) => {
      group.areas.forEach((area) => {
        let slide = pptx.addSlide();
        slide.background = { color: 'f5f5f5' };
        if (area.type === 'text') {
          slide.addText(area.label, { x: 0.5, y: 0.5, fontSize: 20, bold: true });
          slide.addText(area.text, { x: 0.5, y: 1.5, fontSize: 18 });
        } else if (area.type === 'code') {
          slide.addText(area.label, { x: 0.5, y: 0.5, fontSize: 20, bold: true });
          slide.addText(area.text, { x: 0.5, y: 1.5, fontSize: 18, fontFace: 'Courier New' });
        } else if (area.type === 'image' && area.preview) {
          slide.addImage({ data: area.preview, x: 0.5, y: 1, w: 6, h: 4 });
        }
      });
    });

    return pptx;
  };

  const layouts = [
    { id: 1, name: 'Layout 1', generate: generatePPTLayout1, preview: generatePreviewContent1() },
    { id: 2, name: 'Layout 2', generate: generatePPTLayout2, preview: generatePreviewContent2() },
    //  다른 레이아웃 추가
  ];

  const handleOpenModal = (layout) => {
    setSelectedLayout(layout);
    setPreviewContent(layout.preview);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleDownload = () => {
    if (selectedLayout) {
      const pptx = selectedLayout.generate();
      pptx.writeFile({ fileName: `${formData.title}.pptx` });
    }
  };

  return (
    <div>
      <h1>Select a PPT Layout</h1>
      <div className="ppt-layouts">
        {layouts.map(layout => (
          <div
            key={layout.id}
            className="ppt-layout"
            onClick={() => handleOpenModal(layout)}
          >
            {layout.name}
          </div>
        ))}
      </div>
      {selectedLayout && (
        <Modal
          isOpen={isModalOpen}
          onRequestClose={handleCloseModal}
          contentLabel="PPT Layout Preview"
          className="modal"
          overlayClassName="overlay"
        >
          <h2>Preview: {selectedLayout.name}</h2>
          {previewContent}
          <button onClick={handleDownload}>Download Selected Layout</button>
          <button onClick={handleCloseModal}>Close</button>
        </Modal>
      )}
    </div>
  );
}

export default CreatePPT;
